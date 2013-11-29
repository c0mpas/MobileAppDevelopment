package com.example.mada_ueb05;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.EditText;

public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	// snooze_time
	
	public static final String SNOOZE_TIME = "snooze_time";
	public static final String DEFAULT_SNOOZE_TIME = "5";
	
	SharedPreferences prefs;
	
	EditTextPreference prefSnoozeTime;

	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		initializePreferences();
		updatePreferenceSummarys();
	}

	/*
	 * Aktulaisiert die Zusammenfassung nach Aenderungen
	 */
	private void updatePreferenceSummarys() {
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefSnoozeTime.setSummary(prefs.getString(SNOOZE_TIME, DEFAULT_SNOOZE_TIME));
	}

	/*
	 * Initalisiert die Views
	 */
	@SuppressWarnings("deprecation")
	private void initializePreferences() {
		prefSnoozeTime = (EditTextPreference) findPreference(SNOOZE_TIME);
		
		/*
		//Listener, der das TextFeld limitiert
		prefFontsizeHead.getEditText().setOnKeyListener(
				new EditText.OnKeyListener() {
					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (prefFontsizeHead.getEditText().getText().length() > 1) return true;
						return false;
					}
				});
		 */
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals(SNOOZE_TIME)) {
			EditTextPreference pref = (EditTextPreference) findPreference(SNOOZE_TIME);
			pref.setSummary(((EditText) pref.getEditText()).getText());
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
