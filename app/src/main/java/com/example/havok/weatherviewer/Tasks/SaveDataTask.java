package com.example.havok.weatherviewer.Tasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.havok.weatherviewer.DBHelper;

public class SaveDataTask extends AsyncTask<String, Void, Void> {

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public SaveDataTask(final Context context) {
        dbHelper = DBHelper.getInstance(context);
    }

    @Override
    protected Void doInBackground(String ... params) {
        sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_NAME, params[0]);
        contentValues.put(DBHelper.KEY_COUNTRY, params[1]);
        contentValues.put(DBHelper.KEY_DESCRIPTION, params[2]);
        contentValues.put(DBHelper.KEY_HUMIDITY, params[3]);
        contentValues.put(DBHelper.KEY_PRESSURE, params[4]);
        contentValues.put(DBHelper.KEY_TEMPERATURE, params[5]);

        sqLiteDatabase.replace(DBHelper.TABLE_WEATHER, null, contentValues);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        sqLiteDatabase.close();
        dbHelper.close();
    }
}
