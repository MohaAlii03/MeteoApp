// MainData.java
package com.example.meteo;

import com.google.gson.annotations.SerializedName;

public class Coord {
    @SerializedName("long")
    private double longitude;
    @SerializedName("lat")
    private double latitude;


    // Ajoutez d'autres champs selon les données que vous souhaitez extraire

    public double getLongitude() {
        return longitude;
    }
    public double getLatitude() {
        return latitude;
    }

}


// Définissez d'autres méthodes getter ou setter selon les besoins


