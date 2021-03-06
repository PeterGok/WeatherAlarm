package com.mahcks.weatheralarm.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AlarmTable {

	  // Database table
	  public static final String TABLE_ALARMS = "alarms";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_NAME = "name";
	  public static final String COLUMN_TIME = "time";
	  public static final String COLUMN_DAYS = "summary";
	  public static final String COLUMN_IS_SMART = "is_smart";
	  public static final String COLUMN_IS_CRES = "is_cres";
	  public static final String COLUMN_IS_SNOOZE = "is_snooze";
	  public static final String COLUMN_VOLUME = "volume";
	  public static final String COLUMN_IS_ON = "is_on";
	  public static final String COLUMN_EARLY_RAIN = "early_rain";
	  public static final String COLUMN_EARLY_SNOW = "early_snow";
	  public static final String COLUMN_EARLY_FOG = "easy_fog";

	  // Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
	      + TABLE_ALARMS
	      + "("
	      + COLUMN_ID + " integer primary key autoincrement, " 
	      + COLUMN_NAME + " text not null,"
	      + COLUMN_TIME + " text not null," 
	      + COLUMN_DAYS + " text not null,"
	      + COLUMN_IS_SMART + " integer not null,"
	      + COLUMN_IS_CRES + " integer not null,"
	      + COLUMN_IS_SNOOZE + " integer not null,"
	      + COLUMN_VOLUME + " integer not null,"
	      + COLUMN_IS_ON + " integer not null,"
	      + COLUMN_EARLY_RAIN + " integer not null,"
	      + COLUMN_EARLY_SNOW + " integer not null,"
	      + COLUMN_EARLY_FOG + " integer not null"
	      + ");";

	  public static void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	  }

	  public static void onUpgrade(SQLiteDatabase database, int oldVersion,
	      int newVersion) {
	    Log.w(AlarmTable.class.getName(), "Upgrading database from version "
	        + oldVersion + " to " + newVersion
	        + ", which will destroy all old data");
	    database.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARMS);
	    onCreate(database);
	  }
	}
