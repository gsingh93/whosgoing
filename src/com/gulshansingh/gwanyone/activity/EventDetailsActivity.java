package com.gulshansingh.gwanyone.activity;

import java.util.ArrayList;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.TextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.gulshansingh.gwanyone.R;
import com.gulshansingh.gwanyone.network.ServerInterface;
import com.gulshansingh.gwanyone.settings.PreferenceInterface;

public class EventDetailsActivity extends Activity {

	private ServerInterface serverInterface = new ServerInterface();
	private PreferenceInterface prefs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_details);
		prefs = new PreferenceInterface(this);
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
		int id = item.getItemId();
		Intent intent;
		switch (id) {
		case android.R.id.home:
			intent = new Intent(this, EventListActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.menu_refresh:
			populatePeopleGoing();
			break;
		case R.id.menu_edit:
			intent = new Intent(this, EditEventActivity.class);
			intent.putExtra("event_name",
					getIntent().getStringExtra("event_name"));
			startActivity(intent);
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