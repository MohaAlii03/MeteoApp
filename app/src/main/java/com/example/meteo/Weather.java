// Weather.java
package com.example.meteo;

import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("id")
    private int id;
    @SerializedName("main")
    private String main;
    @SerializedName("description")
    private String description;

    // Ajoutez d'autres champs selon les données que vous souhaitez extraire

    public int getId() {
        return id;
    }
    public String getMain() {
        return main;
    }
    public String getDescription() {
        return description;
    }

    // Définissez d'autres méthodes getter ou setter selon les besoins
}
