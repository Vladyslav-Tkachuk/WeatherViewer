package com.example.havok.weatherviewer.Data;

public class WeatherData {
    private int id;
    private String name, country, description, humidity, pressure, temperature;

    public WeatherData(int id, final String name, final String country, final String description,
                       final String humidity, final String pressure, final String temperature) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.description = description;
        this.humidity = humidity;
        this.pressure = pressure;
        this.temperature = temperature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
