package com.example.havok.weatherviewer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final byte DATABASE_VERSION = 1;

    public static final String
            DATABASE_NAME = "WeatherDatabase.db",
            TABLE_WEATHER = "weather",
            KEY_ID = "_id",
            KEY_NAME = "name",
            KEY_COUNTRY = "country",
            KEY_DESCRIPTION = "description",
            KEY_HUMIDITY = "humidity",
            KEY_PRESSURE = "pressure",
            KEY_TEMPERATURE = "temperature";

    private static DBHelper dbHelper;

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBHelper getInstance(final Context context) {
        if (dbHelper == null)
            dbHelper = new DBHelper(context);

        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_WEATHER + "(" + KEY_ID + " integer primary key,"
        + KEY_NAME + " text," + KEY_COUNTRY + " text," + KEY_DESCRIPTION + " text,"
                + KEY_HUMIDITY + " text," + KEY_PRESSURE + " text," + KEY_TEMPERATURE + " text" +");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_WEATHER);

        onCreate(sqLiteDatabase);
    }
}
