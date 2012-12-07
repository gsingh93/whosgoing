package com.gulshansingh.gwanyone.activity;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.TextView;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.gulshansingh.gwanyone.Event;
import com.gulshansingh.gwanyone.db.DatabaseHelper;

public class EventCursorAdapter extends CursorAdapter {

	@SuppressWarnings("deprecation")
	public EventCursorAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		Event event = DatabaseHelper.getEventFromCursor(cursor);
		TextView eventNameTextView = (TextView) view.findViewById(android.R.id.text1);
		eventNameTextView.setText(event.getName());
		view.setTag(event);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflator = LayoutInflater.from(context);
		return inflator.inflate(android.R.layout.simple_list_item_1);
	}
}
