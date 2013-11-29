package com.example.mada_ueb05;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

public class ConfAlarm extends Activity {

	private TimePickerDialog timePicker;
	private DatePickerDialog datePicker;
	private Switch alarmSwitch;
	
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	
	private TextView selDate;
	private TextView selTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conf_alarm);

		refViews();
		setDateTime();
		setListeners();

	}
	
	private void setDateTime(){
		Time dtNow = new Time();
		dtNow.setToNow();
		
		hour= dtNow.hour;
		minute = dtNow.minute;
		
		year = dtNow.year; 
		month = dtNow.month;
		day = dtNow.monthDay;
		
		setDate();
		setTime();
		
	}
	
	private void setTime(){
		
		String hourStr = String.valueOf(hour).toString();
		String minuteStr = String.valueOf(minute).toString();
		
		if( hourStr.length() < 2)
			hourStr = "0"+hourStr;
		
		if( minuteStr.length() < 2)
			minuteStr = "0"+minuteStr;
		
		selTime.setText(hourStr+":"+minuteStr+" Uhr");
		
	}
	
	private void setDate(){
		
		String monthStr = String.valueOf(month+1).toString();
		String dayStr = String.valueOf(day).toString();
		
		if( monthStr.length() < 2)
			monthStr = "0"+monthStr;
		
		if( dayStr.length() < 2)
			dayStr = "0"+dayStr;
		
		selDate.setText(dayStr+"."+monthStr+"."+year);
		
	}
	
	private void setListeners(){
		
		
		
		final TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
				hour = hourOfDay;
				minute = minuteOfHour;
				setTime();
				
			}
		};
		
		selTime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				timePicker = new TimePickerDialog(ConfAlarm.this, timeListener, hour, minute, true);
				timePicker.show();
				
			}
		});
		
		
		final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int selectedYear, int monthOfYear,
					int dayOfMonth) {
				year = selectedYear;
				month = monthOfYear;
				day = dayOfMonth;
				
				setDate();
				
			}
		};
		
		selDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				datePicker = new DatePickerDialog(ConfAlarm.this, dateListener, year, month, day);
				datePicker.show();
				
			}
		});
		
	}

	private void refViews() {

		selDate = (TextView) findViewById(R.id.selectedDate);
		selTime = (TextView) findViewById(R.id.selectedTime);
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
