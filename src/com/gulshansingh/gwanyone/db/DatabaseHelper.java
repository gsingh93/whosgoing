package com.gulshansingh.gwanyone.db;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gulshansingh.gwanyone.Event;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "gwanyone_db";
	// private static final String TABLE_CACHE = "cache";
	public static final String TABLE_EVENTS = "events";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_TIMESTAMP = "timestamp";
	public static final String COLUMN_DETAILS = "details";

	// @formatter:off
	private static final String QUERY_CREATE_TABLE =
			"CREATE TABLE "	+ TABLE_EVENTS + "( " +
			COLUMN_ID + " integer primary key autoincrement, " +
			COLUMN_NAME + " text not null, " +
			COLUMN_TIMESTAMP + " int not null, " +
			COLUMN_DETAILS + " text not null);";
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
		values.put(COLUMN_NAME, event.getName());
		values.put(COLUMN_TIMESTAMP, event.getDate().getTime());
		values.put(COLUMN_DETAILS, event.getDetails());
		db.insert(TABLE_EVENTS, null, values);
		db.close();
	}

	public void deleteEvent(String eventName) {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(TABLE_EVENTS, COLUMN_NAME + "=?", new String[] { eventName });
		db.close();
	}

	public Cursor getEventsCursor() {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(DatabaseHelper.TABLE_EVENTS, new String[] {
				DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME }, null,
				null, null, null, null);
		return cursor;
	}

	// TODO
	public Event getEvent(int eventId) {
		SQLiteDatabase db = getReadableDatabase();
		// Cursor cursor = db.query(
		// DatabaseHelper.TABLE_EVENTS,
		// new String[] { DatabaseHelper.COLUMN_ID,
		// DatabaseHelper.COLUMN_NAME },
		// COLUMN_NAME + "=? AND " + COLUMN_TIMESTAMP + "=?" + " AND "
		// + COLUMN_DETAILS + "=?",
		// new String[] { event.getName(),
		// String.valueOf(event.getDate().getTime()),
		// event.getDetails() }, null, null, null);
		Cursor cursor = db.query(DatabaseHelper.TABLE_EVENTS,
				new String[] { DatabaseHelper.COLUMN_ID,
						DatabaseHelper.COLUMN_NAME,
						DatabaseHelper.COLUMN_TIMESTAMP,
						DatabaseHelper.COLUMN_DETAILS }, COLUMN_ID + "=?",
				new String[] { String.valueOf(eventId) }, null, null, null);

		cursor.moveToFirst();
		return getEventFromCursor(cursor);
	}

	private Event getEventFromCursor(Cursor c) {
		int nameIndex = c.getColumnIndex(COLUMN_NAME);
		int timestampIndex = c.getColumnIndex(COLUMN_TIMESTAMP);
		int detailsIndex = c.getColumnIndex(COLUMN_DETAILS);

		String name = c.getString(nameIndex);
		int timestamp = c.getInt(timestampIndex);
		String details = c.getString(detailsIndex);

		return new Event(name, new Date(timestamp), details);
	}
}
