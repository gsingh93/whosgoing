package com.gulshansingh.gwanyone.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.Toast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.gulshansingh.gwanyone.Event;
import com.gulshansingh.gwanyone.R;
import com.gulshansingh.gwanyone.db.DatabaseHelper;
import com.gulshansingh.gwanyone.ui.DatePickerFragment;
import com.gulshansingh.gwanyone.ui.TimePickerFragment;

public class EditEventActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_event);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		String eventName = getIntent().getStringExtra("event_name");

		Date date;
		if (eventName != null) {
			DatabaseHelper helper = new DatabaseHelper(this);
			Event event = helper.getEvent(eventName);
			initWidgets(event);
			date = event.getDate();
		} else {
			date = new Date();
		}

		SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a",
				Locale.US);
		TextView spinnerTimePicker = (TextView) findViewById(R.id.spinner_time_chooser);
		spinnerTimePicker.setText(timeFormatter.format(date));
		TimePickerFragment.initView(
				(TextView) findViewById(R.id.spinner_time_chooser), date);

		TextView spinnerDatePicker = (TextView) findViewById(R.id.spinner_date_chooser);
		SimpleDateFormat dateFormatter = new SimpleDateFormat(
				"EEE, MMM d, yyyy", Locale.US);
		spinnerDatePicker.setText(dateFormatter.format(date));
		DatePickerFragment.initView(
				(TextView) findViewById(R.id.spinner_date_chooser), date);
	}

	public void onDateSpinnerClick(View v) {
		DatePickerFragment d = new DatePickerFragment();
		d.show(getSupportFragmentManager());
	}

	public void onTimeSpinnerClick(View v) {
		TimePickerFragment d = new TimePickerFragment();
		d.show(getSupportFragmentManager());
	}

	private void initWidgets(Event event) {
		EditText editTextName = (EditText) findViewById(R.id.event_name);
		EditText editTextDetails = (EditText) findViewById(R.id.event_details);

		editTextName.setText(event.getName());
		editTextName.setSelection(event.getName().length());
		editTextDetails.setText(event.getDetails());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_edit_event, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}
}
