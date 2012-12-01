package com.gulshansingh.gwanyone.settings;

import org.holoeverywhere.preference.EditTextPreference;
import org.holoeverywhere.preference.Preference;
import org.holoeverywhere.preference.Preference.OnPreferenceChangeListener;
import org.holoeverywhere.preference.PreferenceActivity;
import org.holoeverywhere.widget.Toast;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;

import com.actionbarsherlock.view.MenuItem;
import com.gulshansingh.gwanyone.R;
import com.gulshansingh.gwanyone.activity.EventListActivity;

public class SettingsActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		addPreferencesFromResource(R.xml.pref_general);
		EditTextPreference e = (EditTextPreference) findPreference("usernamePref");
		e.setSummary(e.getText().toString());

		e.getEditText().setFilters(
				new InputFilter[] { new InputFilter.LengthFilter(15) });
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			Intent intent = new Intent(this, EventListActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}
}