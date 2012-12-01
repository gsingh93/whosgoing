package com.gulshansingh.gwanyone;

import android.content.Intent;
import android.os.Bundle;

import com.WazaBe.HoloEverywhere.sherlock.SActivity;
import com.WazaBe.HoloEverywhere.widget.EditText;
import com.WazaBe.HoloEverywhere.widget.Toast;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.gulshansingh.gwanyone.db.DatabaseHelper;

public class CreateEventActivity extends SActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_create_event, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Toast toast = Toast.makeText(this, "Not implemented yet",
				Toast.LENGTH_SHORT);
		int id = item.getItemId();
		DatabaseHelper helper = new DatabaseHelper(this);
		switch (id) {
		case R.id.menu_save_new_event:
			EditText eventNameEditText = (EditText) findViewById(R.id.event_name);
			String eventName = eventNameEditText.getText().toString();
			if (!eventName.equals("")) {
				helper.addEvent(eventName);
				Intent intent = new Intent(this, EventListActivity.class);
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
