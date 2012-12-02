package com.gulshansingh.gwanyone.activity;

import java.util.Calendar;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.ListActivity;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.ListView;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.Toast;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.gulshansingh.gwanyone.R;
import com.gulshansingh.gwanyone.db.DatabaseHelper;
import com.gulshansingh.gwanyone.network.ServerInterface;
import com.gulshansingh.gwanyone.notification.NotificationAlarmSetter;
import com.gulshansingh.gwanyone.settings.PreferenceInterface;
import com.gulshansingh.gwanyone.settings.SettingsActivity;
import com.gulshansingh.gwanyone.thirdparty.ChangeLog;

public class EventListActivity extends ListActivity {

	private String user;
	private int week;

	private PreferenceInterface prefs;
	private ServerInterface serverInterface = new ServerInterface();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_list);
		prefs = new PreferenceInterface(this);

		init();

		DatabaseHelper db = new DatabaseHelper(this);
		Cursor cursor = db.getEventsCursor();
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_1, cursor,
				new String[] { DatabaseHelper.COLUMN_NAME },
				new int[] { android.R.id.text1 });
		setListAdapter(adapter);

		ListView list = getListView();
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getApplicationContext(),
						EventDetailsActivity.class);
				// Integer eventId = (Integer) ((TextView) view).getTag();
				intent.putExtra("event_id", (int) id); // TODO
				startActivity(intent);
			}
		});
		list.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				String eventName = ((TextView) view
						.findViewById(android.R.id.text1)).getText().toString();
				DatabaseHelper db = new DatabaseHelper(getApplicationContext());
				db.deleteEvent(eventName);
				updateAdapter();
				return false;
			}
		});

		displayChangelog();
		displayMessage();
	}

	private void displayChangelog() {
		ChangeLog cl = new ChangeLog(this);
		if (cl.firstRun())
			cl.getLogDialog().show();
	}

	private void displayMessage() {
		// TODO
	}

	private void promptUsername() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		LayoutInflater li = getLayoutInflater();
		final LinearLayout layout = (LinearLayout) li.inflate(
				R.layout.dialog_prompt_username, null);

		builder.setTitle("Choose username").setView(layout)
				.setCancelable(false)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int button) {
						// Do nothing for now
					}
				});
		final AlertDialog d = builder.show();

		android.widget.Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				EditText editText = (EditText) layout
						.findViewById(R.id.username_input);
				user = editText.getText().toString();
				if (!user.equals("")) {
					prefs.setUsername(user);
					serverInterface.registerUser(user);
					d.dismiss();
				} else {
					Toast.makeText(getApplicationContext(),
							"That's not a valid username!", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
	}

	private int getCurrentWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(4);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	private void init() {
		user = prefs.getUsername();
		// If this is a first time use, set the alarm and get a username
		if (user == null) {
			NotificationAlarmSetter.setAlarm(this);
			promptUsername();
		}

		int curWeek = getCurrentWeek();
		week = prefs.getWeek(curWeek);
		// If we are loading this for the first time this week, update the week
		// and set answered to false
		if (week != curWeek) {
			week = curWeek;
			prefs.setAnswered(false);
		}
	}

	private void updateAdapter() {
		DatabaseHelper db = new DatabaseHelper(this);
		CursorAdapter ca = (CursorAdapter) getListAdapter();
		Cursor c = ca.getCursor();
		c.close();
		ca.changeCursor(db.getEventsCursor());
	}

	@Override
	public void onResume() {
		super.onResume();
		updateAdapter();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_event_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Toast toast = Toast.makeText(this, "Not implemented yet",
				Toast.LENGTH_SHORT);
		int id = item.getItemId();
		Intent intent;
		switch (id) {
		case R.id.menu_new_event:
			intent = new Intent(this, EditEventActivity.class);
			startActivity(intent);
			break;
		case R.id.menu_refresh:
			toast.show();
			break;
		case R.id.menu_settings:
			intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}
}
