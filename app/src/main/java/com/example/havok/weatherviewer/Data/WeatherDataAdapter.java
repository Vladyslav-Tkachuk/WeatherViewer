package com.example.havok.weatherviewer.Data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.havok.weatherviewer.R;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataAdapter extends ArrayAdapter {
    private List list = new ArrayList();

    public WeatherDataAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(final WeatherData weatherData) {
        list.add(weatherData);
        super.add(weatherData);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        WeatherDataHolder weatherDataHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.fragment_weather_offline, parent, false);

            weatherDataHolder = new WeatherDataHolder();
            weatherDataHolder.name = convertView.findViewById(R.id.t_name);
            weatherDataHolder.country = convertView.findViewById(R.id.t_country);
            weatherDataHolder.description = convertView.findViewById(R.id.t_description);
            weatherDataHolder.humidity = convertView.findViewById(R.id.t_humidity);
            weatherDataHolder.pressure = convertView.findViewById(R.id.t_pressure);
            weatherDataHolder.temperature = convertView.findViewById(R.id.t_temperature);

            convertView.setTag(weatherDataHolder);
        } else
            weatherDataHolder = (WeatherDataHolder) convertView.getTag();


        WeatherData weatherData = (WeatherData) getItem(position);

        weatherDataHolder.name.setText(weatherData.getName());
        weatherDataHolder.country.setText(weatherData.getCountry());
        weatherDataHolder.description.setText(weatherData.getDescription());
        weatherDataHolder.humidity.setText(weatherData.getHumidity());
        weatherDataHolder.pressure.setText(weatherData.getPressure());
        weatherDataHolder.temperature.setText(weatherData.getTemperature());

        weatherDataHolder.name.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10);
        weatherDataHolder.country.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10);
        weatherDataHolder.description.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10);
        weatherDataHolder.humidity.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10);
        weatherDataHolder.pressure.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10);
        weatherDataHolder.temperature.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10);

        return convertView;
    }

    static class WeatherDataHolder {
        TextView name, country, description, humidity, pressure, temperature;
    }
}
