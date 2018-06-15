package com.example.havok.weatherviewer.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.havok.weatherviewer.MainActivity;
import com.example.havok.weatherviewer.R;
import com.example.havok.weatherviewer.Utils;

import org.json.JSONObject;

import java.util.Locale;

public class WeatherOnlineFragment extends Fragment {
    private Typeface weatherFont;

    private TextView location, details, temperature;

    private Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler();
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_online, container, false);

        location = view.findViewById(R.id.location);
        details = view.findViewById(R.id.details);
        temperature = view.findViewById(R.id.temperature);

        location.setTypeface(weatherFont);
        details.setTypeface(weatherFont);
        temperature.setTypeface(weatherFont);

        Button buttonChangeCity = view.findViewById(R.id.button_change_city);
        buttonChangeCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).showChangeLocationDialog();
            }
        });

        Button buttonShowData = view.findViewById(R.id.button_show_data);
        buttonShowData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeatherOfflineFragment weatherOfflineFragment = new WeatherOfflineFragment();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, weatherOfflineFragment)
                        .commit();
            }
        });

        updateWeather();

        return view;
    }

    private void updateWeather() {
        new Thread() {
            @Override
            public void run() {
                final JSONObject json = Utils.getJSON(getActivity(), ((MainActivity) getActivity()).getLocation());

                if(json == null) {
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        public void run() {
                            try {
                                String tmp = json.getString("name").toUpperCase(Locale.US) +
                                        ", " + json.getJSONObject("sys").getString("country");

                                location.setText(tmp);

                                JSONObject weather = json.getJSONArray("weather").getJSONObject(0);
                                JSONObject main = json.getJSONObject("main");

                                tmp = (weather.getString("description").toUpperCase(Locale.US) +
                                        "\n" + getActivity().getString(R.string.humidity) + ": " + main.getString("humidity") + "%" +
                                        "\n" + getActivity().getString(R.string.pressure) + ": " + main.getString("pressure") + " hPa");

                                details.setText(tmp);

                                tmp = String.format(Locale.US, "%.2f", main.getDouble("temp")) + " ℃";

                                temperature.setText(tmp);

                                Utils.saveJSONtoSQLite(getActivity(), json);

                            } catch(Exception exp) {
                                Log.e(MainActivity.TAG, "One or more fields not found in the JSON data");
                            }
                        }
                    });
                }
            }
        }.start();
    }

    public void changeLocation(final String location) {
        ((MainActivity) getActivity()).setLocation(location);
        updateWeather();
    }
}
