package com.example.mada_ueb05;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AlarmActivity extends Activity {

	private static final int ALARMTIME = 30000;
	
	private Button off;
	private Button snooze;
	private MediaPlayer player;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		referenceViews();
		setListeners();
		player = MediaPlayer.create(this, R.raw.alarm);
		startAlarm();
	}

	private void referenceViews() {
		off = (Button) findViewById(R.id.button_off);
		snooze = (Button) findViewById(R.id.button_snooze);
	}
	
	private void setListeners() {
		off.setOnClickListener(offListener);
		snooze.setOnClickListener(snoozeListener);
	}
	
	private void startAlarm() {
		player.setLooping(true);
		player.start();
		SystemClock.sleep(ALARMTIME);
		player.stop();
	}

	private void stopAlarm() {
		player.stop();
	}

	private void snoozeAlarm() {
		// snooze alarm sound
	}
	
	private OnClickListener offListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			stopAlarm();
		}
	};

	private OnClickListener snoozeListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			snoozeAlarm();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu
		getMenuInflater().inflate(R.menu.alarm, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.action_settings:
				startActivity(new Intent(this, SettingsActivity.class));
				break;
			default:
				break;
		}
		return true;
	}

}
