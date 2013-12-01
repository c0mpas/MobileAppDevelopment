package com.example.mada_ueb05;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	public static final String SNOOZE_TIME = "snooze_time";
	public static final String DEFAULT_SNOOZE_TIME = "5";
	public static final int DEFAULT_SNOOZE_TIME_INT = 5;
	
	private SharedPreferences prefs;
	private EditTextPreference prefSnoozeTime;
	
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		initializePreferences();
		updatePreferenceSummarys();
	}

	/*
	 * Aktulaisiert die Zusammenfassung nach Aenderungen
	 */
	private void updatePreferenceSummarys() {
		prefSnoozeTime.setSummary(prefs.getString(SNOOZE_TIME, DEFAULT_SNOOZE_TIME));
	}

	/*
	 * Initalisiert die Views
	 */
	@SuppressWarnings("deprecation")
	private void initializePreferences() {
		prefSnoozeTime = (EditTextPreference) findPreference(SNOOZE_TIME);
		
		// Listener, der das TextFeld limitiert
		prefSnoozeTime.getEditText().setOnKeyListener(
			new EditText.OnKeyListener() {
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					if (prefSnoozeTime.getEditText().getText().length() > 1) return true;
					return false;
				}
			});
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals(SNOOZE_TIME)) {
			// Check if value is ok (if not, set to default)
			int snoozetime;
			try {
				snoozetime = Integer.parseInt(prefs.getString(SNOOZE_TIME, DEFAULT_SNOOZE_TIME));
			} catch (Exception e) {
				snoozetime = 0;
			}
			if (snoozetime < 1) prefs.edit().putString(SNOOZE_TIME, DEFAULT_SNOOZE_TIME).commit();
			updatePreferenceSummarys();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}

}
