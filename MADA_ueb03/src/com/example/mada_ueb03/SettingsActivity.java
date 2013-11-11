package com.example.mada_ueb03;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.EditText;

public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	
	public static final String DEFAULT_HEAD_SIZE = "14";
	public static final String DEFAULT_TAIL_SIZE = "10";
	public static final String DEFAULT_THEME = "0";
	public static final String FONTSIZE_HEAD = "fontsize_head";
	public static final String FONTSIZE_TAIL = "fontsize_tail";
	public static final String THEME = "theme";

	public static final String THEME_ANDROID = "Android";
	public static final String THEME_KITTY = "Kitty";
	public static final String THEME_DARK = "Dark";
	private String[] themes = {THEME_ANDROID, THEME_KITTY, THEME_DARK};
	
	SharedPreferences prefs;
	
	EditTextPreference prefFontsizeHead;
	EditTextPreference prefFontsizeTail;
	ListPreference prefTheme;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		referencePreferences();
		prefTheme.setEntries(themes);
		updatePreferenceSummarys();
	}

	private void updatePreferenceSummarys() {
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		prefFontsizeHead.setSummary(prefs.getString(FONTSIZE_HEAD, DEFAULT_HEAD_SIZE));
		prefFontsizeTail.setSummary(prefs.getString(FONTSIZE_TAIL, DEFAULT_TAIL_SIZE));
		prefTheme.setSummary(prefTheme.getEntry());
	}
    
	@SuppressWarnings("deprecation")
	private void referencePreferences() {
		prefFontsizeHead = (EditTextPreference) findPreference(FONTSIZE_HEAD);
		prefFontsizeTail = (EditTextPreference) findPreference(FONTSIZE_TAIL);
		prefTheme = (ListPreference) findPreference(THEME);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals(FONTSIZE_HEAD)) {
			prefFontsizeHead.setSummary(((EditText) prefFontsizeHead.getEditText()).getText());
		} else if (key.equals(FONTSIZE_TAIL)) {
			prefFontsizeTail.setSummary(((EditText) prefFontsizeTail.getEditText()).getText());
		} else if (key.equals(THEME)) {
			prefTheme.setSummary(prefTheme.getEntry());
		}
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
    
}
