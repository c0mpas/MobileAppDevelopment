package com.example.mada_ueb04;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.ResultReceiver;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class DownloadService extends IntentService {

	private static final String DOWNLOAD_SERVICE = "DownloadService";
	public static final String PROGRESS = "progress";
	public static final int UPDATE_PROGRESS = 1005;
	public static final String INTENT_KEY = "myawesomekey";

	private String filePath;
	private NotificationManager mNotifyManager;
	private NotificationCompat.Builder mBuilder;

	public DownloadService() {

		super(DOWNLOAD_SERVICE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, startId, startId);
		return START_STICKY;
	}


	@Override
	protected void onHandleIntent(Intent intent) {

		mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mBuilder = new NotificationCompat.Builder(this);
		mBuilder.setContentTitle(getString(R.string.notification_header))
				.setContentText(getString(R.string.downloadProgress)+getString(R.string.zeroPercent))
				.setSmallIcon(R.drawable.ic_launcher)
				.setProgress(100, 0, false);
			

		filePath = Environment.getExternalStorageDirectory().toString();

		Log.d("SERVICE", "Start download");

		String urlToDownload = intent.getStringExtra(MainActivity.URL);
		String saveName = intent.getStringExtra(MainActivity.SAVE_NAME);
		Intent resuktIntent = new Intent();
		resuktIntent.setAction(INTENT_KEY);
        

		try {
			URL url = new URL(urlToDownload);
			URLConnection connection = url.openConnection();
			connection.connect();
			// this will be useful so that you can show a typical 0-100%
			// progress bar
			int fileLength = connection.getContentLength();

			// download the file
			InputStream input = new BufferedInputStream(url.openStream());
			OutputStream output = new FileOutputStream(filePath + "/"+saveName);

			int progress = 0;
			int lastProgress = 0;

			byte data[] = new byte[16384];
			long total = 0;
			int count;
			while ((count = input.read(data)) != -1) {
				total += count;
				// publishing the progress....
				progress = (int) (total * 100 / fileLength);

				if (lastProgress < progress) {

			        resuktIntent.putExtra(PROGRESS, progress);
			        this.sendBroadcast(resuktIntent);

					mBuilder.setProgress(100, progress, false).setContentText(
							getString(R.string.downloadProgress) + progress
									+ getString(R.string.percent));
					// Displays the progress bar for the first time.
					mNotifyManager.notify(0, mBuilder.build());

					lastProgress = progress;
				}

				if (progress == 100)
					mBuilder.setProgress(100, progress, false).setContentText(
							getString(R.string.finishedDownload));
				// Displays the progress bar for the first time.
				mNotifyManager.notify(0, mBuilder.build());

				output.write(data, 0, count);
			}

			output.flush();
			output.close();
			input.close();
		} catch (IOException e) {
			Log.wtf("IOEXCEPTION", e);		
			}
	}
}