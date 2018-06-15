package com.example.havok.weatherviewer.Tasks;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.havok.weatherviewer.DBHelper;
import com.example.havok.weatherviewer.Data.WeatherData;
import com.example.havok.weatherviewer.MainActivity;
import com.example.havok.weatherviewer.R;
import com.example.havok.weatherviewer.Data.WeatherDataAdapter;

public class LoadDataTask extends AsyncTask<String, WeatherData, String> {
    private DBHelper dbHelper;
    private WeatherDataAdapter weatherDataAdapter;
    private SQLiteDatabase sqLiteDatabase;

    private ListView listView;

    public LoadDataTask(final Context context) {
        dbHelper = DBHelper.getInstance(context);

        weatherDataAdapter= new WeatherDataAdapter(context, R.layout.fragment_weather_offline);

        listView = ((Activity) context).findViewById(R.id.listView);
    }

    @Override
    protected String doInBackground(String ... params) {
        sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_WEATHER, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            WeatherData weatherData1 = new WeatherData(
                    cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.KEY_COUNTRY)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.KEY_HUMIDITY)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PRESSURE)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TEMPERATURE))
            );
            publishProgress(weatherData1);

            do {
                WeatherData weatherData = new WeatherData(
                        cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.KEY_COUNTRY)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.KEY_HUMIDITY)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PRESSURE)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TEMPERATURE))
                );
                publishProgress(weatherData);
            } while (cursor.moveToNext());
        } else {
            Log.d(MainActivity.TAG, "EMPTY");
        }

        cursor.close();

        return null;
    }

    @Override
    protected void onProgressUpdate(WeatherData... values) {
        weatherDataAdapter.add(values[0]);
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);

        listView.setAdapter(weatherDataAdapter);

        sqLiteDatabase.close();
        dbHelper.close();
    }
}
