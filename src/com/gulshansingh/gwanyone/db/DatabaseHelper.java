package com.gulshansingh.gwanyone.db;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.gulshansingh.gwanyone.Event;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "gwanyone.db";

	public static class Events implements BaseColumns {
		public static final String TABLE_EVENTS = "events";
		public static final String _ID = BaseColumns._ID;
		public static final String NAME = "name";
		public static final String TIMESTAMP = "timestamp";
		public static final String DETAILS = "details";
	}

	// @formatter:off
	private static final String QUERY_CREATE_TABLE =
			"CREATE TABLE "	+ Events.TABLE_EVENTS + "( " +
			Events._ID + " integer primary key autoincrement, " +
			Events.NAME + " text not null, " +
			Events.TIMESTAMP + " int not null, " +
			Events.DETAILS + " text not null);";
	// @formatter:on

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(QUERY_CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public void addEvent(Event event) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(Events.NAME, event.getName());
		values.put(Events.TIMESTAMP, event.getDate().getTime());
		values.put(Events.DETAILS, event.getDetails());
		db.insert(Events.TABLE_EVENTS, null, values);
		db.close();
	}

	public void deleteEvent(String eventName) {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(Events.TABLE_EVENTS, Events.NAME + "=?",
				new String[] { eventName });
		db.close();
	}

	public Cursor getEventsCursor() {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(Events.TABLE_EVENTS, new String[] {
				Events._ID, Events.NAME, Events.TIMESTAMP, Events.DETAILS },
				null, null, null, null, null);
		return cursor;
	}

	// TODO
	public Event getEvent(int eventId) {
		SQLiteDatabase db = getReadableDatabase();
		// Cursor cursor = db.query(
		// DatabaseHelper.Events.TABLE_EVENTS,
		// new String[] { DatabaseHelper.COLUMNEvents._ID,
		// DatabaseHelper.COLUMN_Events.NAME },
		// COLUMN_Events.NAME + "=? AND " + COLUMN_Events.TIMESTAMP + "=?" +
		// " AND "
		// + COLUMN_Events.DETAILS + "=?",
		// new String[] { event.getName(),
		// String.valueOf(event.getDate().getTime()),
		// event.getDetails() }, null, null, null);
		Cursor cursor = db.query(Events.TABLE_EVENTS,
				new String[] { DatabaseHelper.Events._ID,
						DatabaseHelper.Events.NAME,
						DatabaseHelper.Events.TIMESTAMP,
						DatabaseHelper.Events.DETAILS }, Events._ID + "=?",
				new String[] { String.valueOf(eventId) }, null, null, null);

		cursor.moveToFirst();
		return getEventFromCursor(cursor);
	}

	public static Event getEventFromCursor(Cursor c) {
		int nameIndex = c.getColumnIndexOrThrow(Events.NAME);
		int timestampIndex = c.getColumnIndexOrThrow(Events.TIMESTAMP);
		int detailsIndex = c.getColumnIndexOrThrow(Events.DETAILS);

		String name = c.getString(nameIndex);
		long timestamp = c.getLong(timestampIndex);
		String details = c.getString(detailsIndex);

		return new Event(name, new Date(timestamp), details);
	}
}
