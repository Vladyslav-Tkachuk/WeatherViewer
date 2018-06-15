package com.example.havok.weatherviewer;

import android.content.Context;
import android.util.Log;

import com.example.havok.weatherviewer.Tasks.LoadDataTask;
import com.example.havok.weatherviewer.Tasks.SaveDataTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public abstract class Utils {
    private static final String OPEN_WEATHER_MAP_API =
            "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";

    public static JSONObject getJSON(final Context context, final String city) {
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_API, city));

            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.addRequestProperty("x-api-key", context.getString(R.string.open_weather_maps_app_id));

            String tmp;

            StringBuilder stringBuilder = new StringBuilder(1024);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            while((tmp = bufferedReader.readLine()) != null)
                stringBuilder.append(tmp).append("\n");

            bufferedReader.close();
            httpURLConnection.disconnect();

            JSONObject data = new JSONObject(stringBuilder.toString());

            if(data.getInt("cod") != 200)
                return null;

            return data;
        } catch(Exception exp) {
            return null;
        }
    }

    public static void saveJSONtoSQLite(final Context context, final JSONObject jsonObject) {
        try {

            JSONObject weather = jsonObject.getJSONArray("weather").getJSONObject(0);
            JSONObject main = jsonObject.getJSONObject("main");

            SaveDataTask saveDataTask = new SaveDataTask(context);

            saveDataTask.execute(
                    jsonObject.getString("name").toUpperCase(Locale.US),
                    jsonObject.getJSONObject("sys").getString("country"),
                    weather.getString("description").toUpperCase(Locale.US),
                    main.getString("humidity"),
                    main.getString("pressure"),
                    String.format(Locale.US, "%.2f", main.getDouble("temp"))
            );

        } catch (JSONException exp) {
            exp.printStackTrace();
        }
    }

    public static void loadDatafromSQLite(final Context context) {
        LoadDataTask loadDataTask = new LoadDataTask(context);
        loadDataTask.execute();
    }
}
