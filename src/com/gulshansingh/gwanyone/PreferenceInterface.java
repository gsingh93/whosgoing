package com.gulshansingh.gwanyone;

import android.content.SharedPreferences;

public class PreferenceInterface {

	public static final String PREF_NAME = "gwanyone_prefs";
	private static final String USER_KEY = "user";
	private static final String ANSWERED_KEY = "answer";
	private static final String WEEK_KEY = "week";

	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;

	public PreferenceInterface(SharedPreferences prefs) {
		this.prefs = prefs;
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
