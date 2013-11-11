package com.example.mada_ueb03;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingsActivity extends PreferenceActivity {
	
	public static final String DEFAULT_HEAD_SIZE = "14";
	public static final String DEFAULT_TAIL_SIZE = "10";
	public static final String DEFAULT_THEME = "0";
	public static final String FONTSIZE_HEAD = "fontsize_head";
	public static final String FONTSIZE_TAIL = "fontsize_tail";
	public static final String THEME = "theme";
		
	SharedPreferences prefs;
	
	int head, tail, theme;
	
	EditTextPreference prefFontsizeHead;
	EditTextPreference prefFontsizeTail;
	ListPreference prefTheme;
	
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		updatePreferences();
	}

	@SuppressWarnings("deprecation")
	private void updatePreferences() {
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		prefFontsizeHead = (EditTextPreference) findPreference(FONTSIZE_HEAD);
		prefFontsizeTail = (EditTextPreference) findPreference(FONTSIZE_TAIL);
		prefTheme = (ListPreference) findPreference(THEME);
		
		prefFontsizeHead.setSummary(prefs.getString(FONTSIZE_HEAD, DEFAULT_HEAD_SIZE));
		prefFontsizeTail.setSummary(prefs.getString(FONTSIZE_TAIL, DEFAULT_TAIL_SIZE));
		prefTheme.setSummary(prefTheme.getEntry());
		
	}
	
}
