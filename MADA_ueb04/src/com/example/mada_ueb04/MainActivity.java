package com.example.mada_ueb04;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ProgressBar progressBar;
	private Button startDownload;

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			if (intent.getAction().equals(DownloadService.INTENT_KEY)) {

				int progress = intent.getIntExtra("progress", 0);
				progressBar.setProgress(progress);
				if (progress == 100) {
					Toast.makeText(getApplicationContext(),
							"Download erfolgreich", Toast.LENGTH_LONG).show();
				}

			}

		}

	};

	private void referenceViews() {

		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		startDownload = (Button) findViewById(R.id.startDownload);
		startDownload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!isMyServiceRunning()) {
					Intent intent = new Intent(getApplicationContext(), DownloadService.class);
					intent.putExtra(
							"url",
							"http://de.download.nvidia.com/Windows/331.82/331.82-desktop-win8-win7-winvista-32bit-international-whql.exe");

					startService(intent);
				}else
					Toast.makeText(getApplicationContext(), "Download läuft bereits", Toast.LENGTH_SHORT).show();
			}
		});

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		referenceViews();

	}


	@Override
	protected void onPause() {
		unregisterReceiver(receiver);
		super.onPause();
	}

	@Override
	protected void onResume() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(DownloadService.INTENT_KEY);
		registerReceiver(receiver, filter);
		super.onResume();
	}

	private boolean isMyServiceRunning() {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (DownloadService.class.getName().equals(
					service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

}
