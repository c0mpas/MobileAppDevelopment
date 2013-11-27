
package com.example.mada_ueb04;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Sebastian
 */
public class MainActivity extends Activity {

	public static final String SAVE_NAME = "saveName";
	public static final String URL = "url";
	private ProgressBar progressBar;
	private Button startDownload;
	private TextView url;
	private TextView saveName;

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(DownloadService.INTENT_KEY)) {
				// Update progress bar
				int progress = intent.getIntExtra(DownloadService.PROGRESS, 0);
				progressBar.setProgress(progress);
				if (progress == 100) {
					Toast.makeText(getApplicationContext(),
							R.string.download_erfolgreich, Toast.LENGTH_LONG).show();
				}
			}
		}
	};

	private void referenceViews() {
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		startDownload = (Button) findViewById(R.id.startDownload);
		url = (TextView) findViewById(R.id.url);
		saveName = (TextView) findViewById(R.id.saveName);
		
		// Preset save filename (from input url)
		try {
			String[] urlParts = url.getText().toString().split("/");
			if (urlParts != null) saveName.setText(urlParts[urlParts.length-1]);
		} catch (Exception e) {
			// Don't care 
		}

		startDownload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!isMyServiceRunning()) {
					if (verifyInput()) {
						// Create intent and start service
						Intent intent = new Intent(getApplicationContext(), DownloadService.class);
						intent.putExtra(URL, url.getText().toString().trim())
							  .putExtra(SAVE_NAME, saveName.getText().toString().trim());
						startService(intent);
					}
				} else
					Toast.makeText(getApplicationContext(),
							R.string.download_running, Toast.LENGTH_SHORT).show();
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
		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (DownloadService.class.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check url and filename
	 */
	private boolean verifyInput() {
		if (url.getText().toString().trim().isEmpty()) {
			Toast.makeText(this, R.string.url_needed, Toast.LENGTH_SHORT).show();
			return false;
		} else if (saveName.getText().toString().trim().isEmpty()) {
			Toast.makeText(this, R.string.save_name_needed, Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}

	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.main_menu, menu);
//		return super.onCreateOptionsMenu(menu);
//	}
//	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle item selection
//		switch (item.getItemId()) {
//			case R.id.stop_download:
//				Intent intent = new Intent();
//				intent.setAction(DownloadService.INTENT_KEY);				
//				stopService(intent);
//				break;
//				
//			default:
//				break;
//		}
//		return true;
//	}

}

