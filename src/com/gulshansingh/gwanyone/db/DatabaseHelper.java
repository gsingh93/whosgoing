package com.gulshansingh.gwanyone.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "gwanyone_db";
	// private static final String TABLE_CACHE = "cache";
	public static final String TABLE_EVENTS = "events";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	
	private static final String QUERY_CREATE_TABLE = "CREATE TABLE "
			+ TABLE_EVENTS + "( " + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_NAME
			+ " text not null);";

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

	public void addEvent(String eventName) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, eventName);
		db.insert(TABLE_EVENTS, null, values);
		db.close();
	}
	
	public void deleteEvent(String eventName) {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(TABLE_EVENTS, COLUMN_NAME + "=?",
				new String[] { eventName });
		db.close();
	}

	public Cursor getEventsCursor() {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(DatabaseHelper.TABLE_EVENTS, new String[] {
				DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME }, null,
				null, null, null, null);
		return cursor;
	}

}
