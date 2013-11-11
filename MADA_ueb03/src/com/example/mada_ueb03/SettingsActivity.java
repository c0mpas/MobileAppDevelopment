package com.example.mada_ueb03;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {

	public static final String DEFAULT_HEAD_SIZE = "20";
	public static final String DEFAULT_TAIL_SIZE = "16";
	public static final String DEFAULT_THEME = "0";
	public static final String FONTSIZE_HEAD = "fontsize_head";
	public static final String FONTSIZE_TAIL = "fontsize_tail";
	public static final String THEME = "theme";

	public static final String THEME_ANDROID = "Android";
	public static final int THEME_VALUE_ANDROID = 0;
	public static final String THEME_KITTY = "Kitty";
	public static final int THEME_VALUE_KITTY = 1;
	public static final String THEME_DARK = "Dark";
	public static final int THEME_VALUE_DARK = 2;
	private String[] themes = { THEME_ANDROID, THEME_KITTY, THEME_DARK };

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
		initializePreferences();
		updatePreferenceSummarys();
	}

	@SuppressWarnings("deprecation")
	private void updatePreferenceSummarys() {
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefFontsizeHead.setSummary(prefs.getString(FONTSIZE_HEAD,
				DEFAULT_HEAD_SIZE));
		prefFontsizeTail.setSummary(prefs.getString(FONTSIZE_TAIL,
				DEFAULT_TAIL_SIZE));
		prefTheme.setSummary(prefTheme.getEntry());
	}

	@SuppressWarnings("deprecation")
	private void initializePreferences() {
		prefFontsizeHead = (EditTextPreference) findPreference(FONTSIZE_HEAD);
		prefFontsizeHead.getEditText().setOnKeyListener(
				new EditText.OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (prefFontsizeHead.getEditText().getText().length() > 1)
							return true;

						return false;
					}
				});

		prefFontsizeTail = (EditTextPreference) findPreference(FONTSIZE_TAIL);
		prefFontsizeTail.getEditText().setOnKeyListener(
				new EditText.OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (prefFontsizeTail.getEditText().getText().length() > 1)
							return true;

						return false;
					}
				});
		
		prefTheme = (ListPreference) findPreference(THEME);
		prefTheme.setEntries(themes);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals(FONTSIZE_HEAD)) {
			EditTextPreference pref = (EditTextPreference) findPreference(FONTSIZE_HEAD);
			pref.setSummary(((EditText) pref.getEditText()).getText());
		} else if (key.equals(FONTSIZE_TAIL)) {
			EditTextPreference pref = (EditTextPreference) findPreference(FONTSIZE_TAIL);
			pref.setSummary(((EditText) pref.getEditText()).getText());
		} else if (key.equals(THEME)) {
			ListPreference pref = (ListPreference) findPreference(THEME);
			pref.setSummary(pref.getEntry());
		}
		setResult(RESULT_OK);
	}

	@Override
	protected void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}

}
