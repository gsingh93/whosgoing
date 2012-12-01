package com.gulshansingh.gwanyone.activity;

import java.util.Calendar;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.DatePicker;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.TimePicker;
import org.holoeverywhere.widget.Toast;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.gulshansingh.gwanyone.Event;
import com.gulshansingh.gwanyone.R;
import com.gulshansingh.gwanyone.db.DatabaseHelper;

public class EditEventActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_event);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		String eventName = getIntent().getStringExtra("event_name");
		if (eventName != null) {
			DatabaseHelper helper = new DatabaseHelper(this);
			Event event = helper.getEvent(eventName);
			initWidgets(event);
		}
	}

	private void initWidgets(Event event) {
		EditText editTextName = (EditText) findViewById(R.id.event_name);
		DatePicker datePicker = (DatePicker) findViewById(R.id.event_date);
		TimePicker timePicker = (TimePicker) findViewById(R.id.event_time);
		EditText editTextDetails = (EditText) findViewById(R.id.event_details);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(event.getDate());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		editTextName.setText(event.getName());
		datePicker.setMinDate(System.currentTimeMillis() - 1000);
		datePicker.updateDate(year, month, dayOfMonth);
		timePicker.setCurrentHour(hour);
		timePicker.setCurrentMinute(minute);
		editTextDetails.setText(event.getDetails());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_edit_event, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Toast toast = Toast.makeText(this, "Not implemented yet",
				Toast.LENGTH_SHORT);
		int id = item.getItemId();
		DatabaseHelper helper = new DatabaseHelper(this);
		Intent intent;
		switch (id) {
		case android.R.id.home:
			intent = new Intent(this, EventDetailsActivity.class);
			intent.putExtra("event_name",
					getIntent().getStringExtra("event_name"));
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.menu_save:
			EditText eventNameEditText = (EditText) findViewById(R.id.event_name);
			String eventName = eventNameEditText.getText().toString();
			if (!eventName.equals("")) {
				helper.addEvent(eventName);
				intent = new Intent(this, EventListActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			} else {
				Toast.makeText(this, "You must specify an event name",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.menu_new_event:
			toast.show();
			break;
		case R.id.menu_refresh:
			toast.show();
			break;
		case R.id.menu_settings:
			toast.show();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}
}
