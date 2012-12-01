package com.gulshansingh.gwanyone.notification;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmSetter extends BroadcastReceiver {

	private static final String TAG = AlarmSetter.class.getName();

	private static final int MILLISECONDS_IN_WEEK = 7 * 24 * 60 * 60 * 1000;

	@Override
	public void onReceive(Context context, Intent intent) {
		setAlarm(context);
	}

	private static long getTimeOffset(int day) {
		Calendar timeOffset = Calendar.getInstance();

		int days = Math.abs(day - timeOffset.get(Calendar.DAY_OF_WEEK));

		timeOffset.add(Calendar.DATE, days);
		timeOffset.set(Calendar.HOUR, 17);
		timeOffset.set(Calendar.MINUTE, 0);
		timeOffset.set(Calendar.SECOND, 0);
		return timeOffset.getTimeInMillis();
	}

	public static void setAlarm(Context context) {
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		long timeOffset1 = getTimeOffset(Calendar.WEDNESDAY);
		long timeOffset2 = getTimeOffset(Calendar.FRIDAY);

		Log.d(TAG, "Alarm set for " + timeOffset1);
		Log.d(TAG, "Alarm set for " + timeOffset2);

		Intent intent = new Intent(context, NotificationSender.class);
		PendingIntent pIntent1 = PendingIntent.getBroadcast(context, 0, intent,
				0);
		PendingIntent pIntent2 = PendingIntent.getBroadcast(context, 1, intent,
				0);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeOffset1,
				MILLISECONDS_IN_WEEK, pIntent1);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeOffset2,
				MILLISECONDS_IN_WEEK, pIntent2);
	}
}
