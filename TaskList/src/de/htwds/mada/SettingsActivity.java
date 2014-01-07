package de.htwds.mada;

import com.example.tasklist.R;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	
	public static final String DEFAULT_HEAD_SIZE = "14";
	public static final String DEFAULT_TAIL_SIZE = "10";
	public static final String FONTSIZE_HEAD = "fontsize_head";
	public static final String FONTSIZE_TAIL = "fontsize_tail";
	
	SharedPreferences prefs;

	int head, tail;

	EditTextPreference prefFontsizeHead;
	EditTextPreference prefFontsizeTail;
	
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
	@SuppressWarnings("deprecation")
	private void updatePreferenceSummarys() {
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefFontsizeHead.setSummary(prefs.getString(FONTSIZE_HEAD, DEFAULT_HEAD_SIZE));
		prefFontsizeTail.setSummary(prefs.getString(FONTSIZE_TAIL, DEFAULT_TAIL_SIZE));
	}

	/*
	 * Initalisiert die Views
	 */
	@SuppressWarnings("deprecation")
	private void initializePreferences() {
		prefFontsizeHead = (EditTextPreference) findPreference(FONTSIZE_HEAD);
		
		//Listener, der das TextFeld limitiert
		prefFontsizeHead.getEditText().setOnKeyListener(
				new EditText.OnKeyListener() {
					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (prefFontsizeHead.getEditText().getText().length() > 1) return true;
						return false;
					}
				});

		prefFontsizeTail = (EditTextPreference) findPreference(FONTSIZE_TAIL);
		prefFontsizeTail.getEditText().setOnKeyListener(
				new EditText.OnKeyListener() {
					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (prefFontsizeTail.getEditText().getText().length() > 1) return true;
						return false;
					}
				});
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
		}
		setResult(RESULT_OK);
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
