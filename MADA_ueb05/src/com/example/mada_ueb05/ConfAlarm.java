package com.example.mada_ueb05;

import java.util.Calendar;

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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class ConfAlarm extends Activity {

	private AlarmManager mng;
	private PendingIntent pAlarmIntent;

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

		initAlarm();

		refViews();
		setDateTime();
		setListeners();

	}

	private void setDateTime() {
		Time dtNow = new Time();
		dtNow.setToNow();

		hour = dtNow.hour;
		minute = dtNow.minute;

		year = dtNow.year;
		month = dtNow.month;
		day = dtNow.monthDay;

		setDate();
		setTime();

	}

	private void initAlarm() {

		// AlarmManager vom System abrufen
		mng = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		// Hier wird die Komponente eingetragen, die als Action
		// ausgeführt werden soll
		Intent alarmIntent = new Intent(this, AlarmActivity.class);
		/* Erstellen eines PendingIntents für eine Activity */
		pAlarmIntent = PendingIntent.getActivity(this, 0, alarmIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
	}

	private void setTime() {

		String hourStr = String.valueOf(hour).toString();
		String minuteStr = String.valueOf(minute).toString();

		if (hourStr.length() < 2)
			hourStr = "0" + hourStr;

		if (minuteStr.length() < 2)
			minuteStr = "0" + minuteStr;

		selTime.setText(hourStr + ":" + minuteStr + " Uhr");

	}

	private void setDate() {

		String monthStr = String.valueOf(month + 1).toString();
		String dayStr = String.valueOf(day).toString();

		if (monthStr.length() < 2)
			monthStr = "0" + monthStr;

		if (dayStr.length() < 2)
			dayStr = "0" + dayStr;

		selDate.setText(dayStr + "." + monthStr + "." + year);

	}

	private void setListeners() {

		alarmSwitch
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked)
							setMyAlarm();
						else
							disableMyAlarm();

					}
				});

		final TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay,
					int minuteOfHour) {
				hour = hourOfDay;
				minute = minuteOfHour;
				setTime();

			}
		};

		selTime.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				timePicker = new TimePickerDialog(ConfAlarm.this, timeListener,
						hour, minute, true);
				timePicker.show();

			}
		});

		final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int selectedYear,
					int monthOfYear, int dayOfMonth) {
				year = selectedYear;
				month = monthOfYear;
				day = dayOfMonth;

				setDate();

			}
		};

		selDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				datePicker = new DatePickerDialog(ConfAlarm.this, dateListener,
						year, month, day);
				datePicker.show();

			}
		});

	}

	private void refViews() {

		selDate = (TextView) findViewById(R.id.selectedDate);
		selTime = (TextView) findViewById(R.id.selectedTime);
		alarmSwitch = (Switch) findViewById(R.id.alarmSwitch);

	}

	private long calcAlarmTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, hour, minute, 0);
		long startTime = calendar.getTimeInMillis();

		return ( startTime - System.currentTimeMillis());

	}

	private void disableMyAlarm() {

		AlarmManager mng = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		mng.cancel(pAlarmIntent);

	}

	private void setMyAlarm() {

		long alarmTime = calcAlarmTime();

		if (alarmTime < 1){
			Toast.makeText(this, R.string.timeLTOne, Toast.LENGTH_SHORT).show();
			alarmSwitch.setChecked(false);
		}
		else
			// Alarm setzen
			mng.set(AlarmManager.ELAPSED_REALTIME, alarmTime, pAlarmIntent);

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
