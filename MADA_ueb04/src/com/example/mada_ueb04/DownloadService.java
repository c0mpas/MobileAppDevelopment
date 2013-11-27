package com.example.mada_ueb04;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class DownloadService extends IntentService {

	private static final int BUFFERSIZE = 16384;
	private static final String DOWNLOAD_SERVICE = "DownloadService";
	public static final String PROGRESS = "progress";
	public static final int UPDATE_PROGRESS = 1005;
	public static final String INTENT_KEY = "myawesomekey";

	private String filePath;
	private NotificationManager mNotifyManager;
	private NotificationCompat.Builder mBuilder;
	private Handler handler;

	public DownloadService() {
		super(DOWNLOAD_SERVICE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, startId, startId);
		handler = new Handler();
		return START_STICKY;
	}


	@Override
	protected void onHandleIntent(Intent intent) {

		
		// NotificationBar wird initalisiert
		mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mBuilder = new NotificationCompat.Builder(this);
		mBuilder.setContentTitle(getString(R.string.notification_header))
				.setContentText(getString(R.string.downloadProgress)+getString(R.string.zeroPercent))
				.setSmallIcon(R.drawable.ic_launcher)
				.setProgress(100, 0, false);
		
		// Get standard download directory
		filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
		
		String urlToDownload = intent.getStringExtra(MainActivity.URL);
		String saveName = intent.getStringExtra(MainActivity.SAVE_NAME);
		Intent resultIntent = new Intent();
		resultIntent.setAction(INTENT_KEY);
        
		try {
			
			//Connect to file url
			URL url = new URL(urlToDownload);
			URLConnection connection = url.openConnection();
			connection.connect();
			
			// Needed for progress bar
			int fileLength = connection.getContentLength();
			
			// Download the file
			InputStream input = new BufferedInputStream(url.openStream());
			
			OutputStream output = new FileOutputStream(filePath + "/"+saveName);

			int progress = 0;
			int lastProgress = 0;

			byte data[] = new byte[BUFFERSIZE];
			long total = 0;
			int count;
			while ((count = input.read(data)) != -1) {
				total += count;
				// Publish the progress
				progress = (int) (total * 100 / fileLength);

				if (lastProgress < progress) {

			        resultIntent.putExtra(PROGRESS, progress);
			        this.sendBroadcast(resultIntent);

					mBuilder.setProgress(100, progress, false).setContentText(
							getString(R.string.downloadProgress) + progress
									+ getString(R.string.percent));
					// Displays the progress bar for the first time
					mNotifyManager.notify(0, mBuilder.build());

					lastProgress = progress;
				}

				if (progress == 100)
					mBuilder.setProgress(100, progress, false).setContentText(getString(R.string.finishedDownload));
				
				mNotifyManager.notify(0, mBuilder.build());

				output.write(data, 0, count);
			}

			output.flush();
			output.close();
			input.close();
			
			//Catch different Exception with reaction
		} catch (MalformedURLException e) {
			handler.post(new Runnable() {
			    public void run() {
			        Toast.makeText(getApplicationContext(), 
			        		R.string.error_service_url_structure, Toast.LENGTH_LONG).show();
			    }
			 });
		} catch (UnknownHostException e) {
			handler.post(new Runnable() {
			    public void run() {
			        Toast.makeText(getApplicationContext(), 
			        		R.string.error_service_url_host, Toast.LENGTH_LONG).show();
			    }
			 });
		} catch (FileNotFoundException e) {
			handler.post(new Runnable() {
			    public void run() {
			        Toast.makeText(getApplicationContext(), 
			        		R.string.error_service_url_filepath, Toast.LENGTH_LONG).show();
			    }
			 });
		} catch (IOException e) {
			handler.post(new Runnable() {
			    public void run() {
			        Toast.makeText(getApplicationContext(), 
			        		R.string.error_service_download, Toast.LENGTH_LONG).show();
			    }
			 });
		} catch (Exception e) {
			handler.post(new Runnable() {
			    public void run() {
			        Toast.makeText(getApplicationContext(), 
			        		R.string.error_service_general, Toast.LENGTH_LONG).show();
			    }
			 });
		}
	}
}