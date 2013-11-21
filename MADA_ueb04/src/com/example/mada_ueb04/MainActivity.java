package com.example.mada_ueb04;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ProgressBar progressBar;
	
	private ResultReceiver receiver = new ResultReceiver(new Handler()) {

		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {
			super.onReceiveResult(resultCode, resultData);
			if (resultCode == DownloadService.UPDATE_PROGRESS) {
				int progress = resultData.getInt("progress");
				progressBar.setProgress(progress);
				if (progress == 100) {
					Toast.makeText(getApplicationContext(), "Download erfolgreich", Toast.LENGTH_LONG).show();
				}
			}
		}

	};

	private void referenceViews() {

		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		referenceViews();

		Intent intent = new Intent(this, DownloadService.class);
		intent.putExtra("url", "http://de.download.nvidia.com/Windows/331.82/331.82-desktop-win8-win7-winvista-32bit-international-whql.exe");
		intent.putExtra("receiver", receiver);
		startService(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
