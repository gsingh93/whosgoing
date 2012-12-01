package com.gulshansingh.gwanyone;

import org.holoeverywhere.widget.Toast;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO ActionBar up navitgation

		addPreferencesFromResource(R.xml.pref_general);
		EditTextPreference e = (EditTextPreference) findPreference("usernamePref");
		e.setSummary(e.getText().toString());
		e.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				EditTextPreference e = (EditTextPreference) findPreference("usernamePref");
				String username = (String) newValue;
				if (username.equals("")) {
					Toast.makeText(getApplicationContext(),
							"That's not a valid username!", Toast.LENGTH_SHORT)
							.show();
					return false;
				}
				e.setSummary(username);
				return true;
			}
		});
	}
}