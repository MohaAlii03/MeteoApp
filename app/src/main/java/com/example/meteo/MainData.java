// MainData.java
package com.example.meteo;

import com.google.gson.annotations.SerializedName;

public class MainData {
    @SerializedName("temp")
    private double temp;
    @SerializedName("humidity")
    private int humidity;
    @SerializedName("pressure")
    private int pressure;
    @SerializedName("temp_max")
    private double maxtemp;
    @SerializedName("temp_min")
    private double mintemp;

    // Ajoutez d'autres champs selon les données que vous souhaitez extraire

    public int getPressure() {
        return pressure;
    }
    public double getTemp() {
        return temp;
    }
    public int getHumidity() {
        return humidity;
    }
    public double getMaxtemp() {
        return maxtemp;
    }
    public double getMintemp() {
        return mintemp;
    }

    }


    // Définissez d'autres méthodes getter ou setter selon les besoins

