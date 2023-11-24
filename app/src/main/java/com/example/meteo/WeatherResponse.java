package com.example.meteo;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class WeatherResponse {
    @SerializedName("main")
    private MainData main;
    @SerializedName("weather")
    private List<Weather> weather;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("temp")
    private double temp;
    @SerializedName("maxtemp")
    private MainData maxtemp;
    @SerializedName("mintemp")
    private MainData mintemp;
    @SerializedName("name")
    private String name;
    // Ajoutez d'autres champs celon les données que vous souhaitez extraire

    public MainData getMain() {
        return main;
    }
    public MainData getMaxtemp() {
        return main;
    }
    public MainData getMintemp() {
        return main;
    }
    public List<Weather> getWeather() {
        return weather;
    }
    public Wind getWind() {
        return wind;
    }

    public String getName() {
        return name;
    }



    // Définissez d'autres méthodes getter ou setter selon les besoins
}

