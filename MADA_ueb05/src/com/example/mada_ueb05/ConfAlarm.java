package com.example.mada_ueb05;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Switch;
import android.widget.TextView;

public class ConfAlarm extends Activity {

	private TimePickerDialog timePicker;
	private DatePickerDialog datePicker;
	private Switch alarmSwitch;
	
	private TextView selDate;
	private TextView selTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conf_alarm);

		refViews();

	}

	private void refViews() {


		alarmSwitch = (Switch) findViewById(R.id.alarmSwitch);

	}

	private void setMyAlarm() {

		// AlarmManager vom System abrufen
		AlarmManager mng = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		// Hier wird die Komponente eingetragen, die als Action
		// ausgeführt werden soll
		Intent intent = new Intent(this, AlarmActivity.class);
		/* Erstellen eines PendingIntents für eine Activity */
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		/*
		 * Alarm setzen mng.set(AlarmManager.ELAPSED_REALTIME,
		 * SystemClock.elapsedRealtime() , pi);
		 */

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.conf_alarm, menu);
		return true;
	}

}
