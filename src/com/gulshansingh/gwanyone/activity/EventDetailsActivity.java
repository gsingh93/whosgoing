package com.gulshansingh.gwanyone.activity;

import java.util.ArrayList;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.TextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.gulshansingh.gwanyone.Event;
import com.gulshansingh.gwanyone.R;
import com.gulshansingh.gwanyone.network.ServerInterface;
import com.gulshansingh.gwanyone.settings.PreferenceInterface;

public class EventDetailsActivity extends Activity {

	private ServerInterface serverInterface = new ServerInterface();
	private PreferenceInterface prefs;

	private Event event;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_details);
		prefs = new PreferenceInterface(this);
		event = (Event) getIntent().getSerializableExtra(Event.EXTRA_KEY);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(event.getName());

		refresh();
		// populatePeopleGoing();
	}

	private void refresh() {
		serverInterface.getPeopleGoing(new GetPeopleGoingCompletedListener());
		serverInterface
				.getPeopleNotGoing(new GetPeopleNotGoingCompletedListener());
	}

	public interface AsyncTaskCompleteListener<T> {
		public void onComplete(T result);
	}

	private class GetPeopleGoingCompletedListener implements
			AsyncTaskCompleteListener<ArrayList<String>> {
		@Override
		public void onComplete(ArrayList<String> list) {
			populatePeopleGoing(list);
		}
	}

	private class GetPeopleNotGoingCompletedListener implements
			AsyncTaskCompleteListener<ArrayList<String>> {
		@Override
		public void onComplete(ArrayList<String> list) {
			populatePeopleNotGoing(list);
		}
	}

	private void populatePeopleGoing(ArrayList<String> peopleGoing) {
		TextView peopleGoingTextView = (TextView) findViewById(R.id.people_going);

		StringBuilder sb = new StringBuilder();
		for (String person : peopleGoing) {
			sb.append(person).append('\n');
		}
		String peopleGoingString = sb.toString();
		if (peopleGoingString.equals("")) {
			peopleGoingTextView.setText(R.string.no_people_going);
		} else {
			peopleGoingTextView.setText(peopleGoingString);
		}
	}

	private void populatePeopleNotGoing(ArrayList<String> peopleNotGoing) {
		StringBuilder sb = new StringBuilder();
		for (String person : peopleNotGoing) {
			sb.append(person).append('\n');
		}
		String peopleNotGoingString = sb.toString();
		TextView peopleNotGoingTextView = (TextView) findViewById(R.id.people_not_going);
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
			refresh();
			break;
		case R.id.menu_edit:
			intent = new Intent(this, EditEventActivity.class);
			intent.putExtra(Event.EXTRA_KEY, event);
			startActivity(intent);
		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}

	public void yesClicked(View v) {
		serverInterface.answerYes(prefs.getUsername());
		refresh();
		prefs.setAnswered(true);
	}

	public void noClicked(View v) {
		serverInterface.answerNo(prefs.getUsername());
		refresh();
		prefs.setAnswered(true);
	}
}
