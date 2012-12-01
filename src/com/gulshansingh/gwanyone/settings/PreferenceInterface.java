package com.gulshansingh.gwanyone.settings;

import org.holoeverywhere.preference.PreferenceManager;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceInterface {

	private static final String USER_KEY = "usernamePref";
	private static final String ANSWERED_KEY = "answer";
	private static final String WEEK_KEY = "week";

	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;

	public PreferenceInterface(Context context) {
		this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
		this.editor = prefs.edit();
	}
	
	public void setAnswered(boolean bool) {
		editor.putBoolean(ANSWERED_KEY, bool);
		editor.commit();
	}

	public String getUsername() {
		return prefs.getString(PreferenceInterface.USER_KEY, null);
	}

	public boolean getAnswered() {
		return prefs.getBoolean(PreferenceInterface.ANSWERED_KEY, false);
	}

	public void setUsername(String username) {
		editor.putString(PreferenceInterface.USER_KEY, username);
		editor.commit();
	}
	
	public int getWeek(int defaultWeek) {
		return prefs.getInt(PreferenceInterface.WEEK_KEY, defaultWeek);
	}
}
