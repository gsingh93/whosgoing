package com.gulshansingh.gwanyone;

import java.util.ArrayList;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.Toast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.gulshansingh.gwanyone.network.ServerInterface;

public class EventDetailsActivity extends Activity {

	private ServerInterface serverInterface = new ServerInterface();
	private PreferenceInterface prefs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_details);
		prefs = new PreferenceInterface(getSharedPreferences(
				PreferenceInterface.PREF_NAME, MODE_PRIVATE));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		populatePeopleGoing();
	}

	private void populatePeopleGoing() {
		ArrayList<String> peopleGoing = serverInterface.getPeopleGoing();
		ArrayList<String> peopleNotGoing = serverInterface.getPeopleNotGoing();

		StringBuilder sb = new StringBuilder();
		for (String person : peopleGoing) {
			sb.append(person).append('\n');
		}
		String peopleGoingString = sb.toString();

		sb = new StringBuilder();
		for (String person : peopleNotGoing) {
			sb.append(person).append('\n');
		}
		String peopleNotGoingString = sb.toString();

		TextView peopleGoingTextView = (TextView) findViewById(R.id.people_going);
		TextView peopleNotGoingTextView = (TextView) findViewById(R.id.people_not_going);
		if (peopleGoingString.equals("")) {
			peopleGoingTextView.setText(R.string.no_people_going);
		} else {
			peopleGoingTextView.setText(peopleGoingString);
		}
		peopleNotGoingTextView.setText(peopleNotGoingString);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_event_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Toast toast = Toast.makeText(this, "Not implemented yet",
				Toast.LENGTH_SHORT);
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			Intent intent = new Intent(this, EventListActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
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

	public void yesClicked(View v) {
		serverInterface.answerYes(prefs.getUsername());
		populatePeopleGoing();
		prefs.setAnswered(true);
	}

	public void noClicked(View v) {
		serverInterface.answerNo(prefs.getUsername());
		populatePeopleGoing();
		prefs.setAnswered(true);
	}
}
