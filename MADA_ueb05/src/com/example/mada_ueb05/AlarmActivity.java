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
import android.widget.Toast;

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
		new Thread(new Runnable() {
			@Override
			public void run() {
				player.setLooping(true);
				player.start();
				SystemClock.sleep(ALARMTIME);
				player.stop();
			}
		}).start();
	}

	// Stops the alarm sound
	private void stopAlarm() {
		player.stop();
		prefs.edit().putBoolean(ConfAlarm.KEY_ALARM, false).commit();
		Toast.makeText(this, R.string.msg_alarm_off, Toast.LENGTH_SHORT).show();
		SystemClock.sleep(500);
		this.finish();
	}

	// Set new alarm and stop current alarm sound
	private void snoozeAlarm() {
		player.stop();
		
		int snoozetime = Integer.parseInt(prefs.getString(SettingsActivity.SNOOZE_TIME, SettingsActivity.DEFAULT_SNOOZE_TIME));
		// SET NEW ALARM HERE !!!
		
		String message = getString(R.string.msg_alarm_snooze_part1) + snoozetime + getString(R.string.msg_alarm_snooze_part2);
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		
		SystemClock.sleep(500);
		this.finish();
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
