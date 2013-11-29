package com.example.mada_ueb05;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AlarmActivity extends Activity {

	private static final int ALARMTIME = 30000;
	
	private SharedPreferences prefs;
	private Button off;
	private Button snooze;
	private MediaPlayer player;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		// Stop if alarm is off
		if (!prefs.getBoolean(SettingsActivity.ALARM_STATUS, true)) this.finish();
		
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
	
	// Plays the alarm sound
	private void startAlarm() {
		player.setLooping(true);
		player.start();
		SystemClock.sleep(ALARMTIME);
		player.stop();
	}

	// Stops the alarm sound
	private void stopAlarm() {
		player.stop();
		SystemClock.sleep(2000);
		this.finish();
	}

	// Set new alarm and stop current alarm sound
	private void snoozeAlarm() {
		// SET NEW ALARM HERE !!!
		stopAlarm();
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
