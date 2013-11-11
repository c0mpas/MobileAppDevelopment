package com.example.mada_ueb03;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingsActivity extends PreferenceActivity {
	
	public static final String DEFAULT_HEAD_SIZE = "14";
	public static final String DEFAULT_TAIL_SIZE = "10";
	public static final String DEFAULT_THEME = "0";
	public static final String FONTSIZE_HEAD = "fontsize_head";
	
	SharedPreferences prefs;
	
	int head;
	int tail;
	int theme;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		updatePreferences();
	}

	private void updatePreferences() {
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		// head = prefs.getString(FONTSIZE_HEAD, DEFAULT_HEAD_SIZE);
	}
	
}
