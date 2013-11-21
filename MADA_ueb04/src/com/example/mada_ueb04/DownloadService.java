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

	public static final int UPDATE_PROGRESS = 1005;
	
	private String filePath;
	private NotificationManager mNotifyManager;
	private NotificationCompat.Builder mBuilder;

	public DownloadService() {
		



		super("DownloadService");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, startId, startId);
		return START_STICKY;
	}

	// Will be called asynchronously be Android
	@Override
	protected void onHandleIntent(Intent intent) {
		
		mNotifyManager =
		        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mBuilder = new NotificationCompat.Builder(this);
		mBuilder.setContentTitle("Download")
		    .setContentText(getString(R.string.downloadProgress))
		    .setSmallIcon(R.drawable.ic_launcher);
		

		filePath = Environment.getExternalStorageDirectory().toString();
		
		Log.d("SERVICE", "Start download");

		String urlToDownload = intent.getStringExtra("url");
		ResultReceiver receiver = (ResultReceiver) intent
				.getParcelableExtra("receiver");
		try {
			URL url = new URL(urlToDownload);
			URLConnection connection = url.openConnection();
			connection.connect();
			// this will be useful so that you can show a typical 0-100%
			// progress bar
			int fileLength = connection.getContentLength();

			// download the file
			InputStream input = new BufferedInputStream(url.openStream());
			OutputStream output = new FileOutputStream(filePath+"/pic02.exe");

			byte data[] = new byte[1024];
			long total = 0;
			int count;
			while ((count = input.read(data)) != -1) {
				total += count;
				// publishing the progress....
				Bundle resultData = new Bundle();
				
				int progress =  (int) (total * 100 / fileLength);
				resultData.putInt("progress", progress);
				receiver.send(UPDATE_PROGRESS, resultData);
				
				mBuilder.setProgress(100, progress, false)
						.setContentText(getString(R.string.downloadProgress)+progress+getString(R.string.percent));
                // Displays the progress bar for the first time.
                mNotifyManager.notify(0, mBuilder.build());
                
                if(progress == 100)
                	mBuilder.setProgress(100, progress, false)
					.setContentText(getString(R.string.finishedDownload));
            // Displays the progress bar for the first time.
            mNotifyManager.notify(0, mBuilder.build());
                	
				
				output.write(data, 0, count);
			}

			output.flush();
			output.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}