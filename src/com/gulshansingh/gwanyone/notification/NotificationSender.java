package com.gulshansingh.gwanyone.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.gulshansingh.gwanyone.EventDetailsActivity;
import com.gulshansingh.gwanyone.PreferenceInterface;
import com.gulshansingh.gwanyone.R;

public class NotificationSender extends BroadcastReceiver {
	private static final String TAG = NotificationSender.class.getName();

	private NotificationManager notificationManager;
	private Context context;
	public static final int ANSWER_NOTIFICATION_ID = 1;

	private static final int icon = R.drawable.notification_icon;

	private static final CharSequence contentTitle = "Are you going to Gurdwara?";
	private static final String tickerText = "Are you going to Gurdwara?";
	private static final String contentText = "You haven't given an answer yet!";

	@Override
	public void onReceive(Context context, Intent intent) {
		PreferenceInterface prefs = new PreferenceInterface(
				context.getSharedPreferences(PreferenceInterface.PREF_NAME,
						Context.MODE_PRIVATE));

		this.context = context;
		notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		String word = prefs.getAnswered() ? "not" : "";
		Log.d(TAG, "Notification will " + word + " be sent");
		if (!prefs.getAnswered()) {
			sendNotification();
		}
	}

	public void sendNotification() {
		Notification notification = constructNotification(tickerText,
				contentText, createIntent());
		notificationManager.notify(ANSWER_NOTIFICATION_ID, notification);
	}

	private Notification constructNotification(String tickerText,
			String contentText, PendingIntent pIntent) {
		// Show the notification now
		long when = System.currentTimeMillis();

		// Construct the notification TODO
		Notification notification = new Notification(icon, tickerText, when);
		notification.setLatestEventInfo(context, contentTitle, contentText,
				pIntent);

		// Remove the notification after it is clicked
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		return notification;
	}

	private PendingIntent createIntent() {
		Intent intent = new Intent(context, EventDetailsActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		return PendingIntent.getActivity(context, 0, intent, 0);
	}
}
