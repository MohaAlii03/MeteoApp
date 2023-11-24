package com.example.meteo;

// Importation des packages nécessaires pour le fonctionnement de l'application.
import static com.example.meteo.ApiClient.API_KEY;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.example.meteo.WeatherResponse;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meteo.MainData;

import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.meteo.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.SearchView;

import android.view.View;

public class MainActivity extends AppCompatActivity { // Déclaration de la classe principale 'MainActivity' qui étend 'AppCompatActivity'.

    // Déclaration des variables pour les différents éléments de l'interface utilisateur.
    private TextView textViewTemperature;
    private TextView textViewTemperatureF;
    private TextView textViewPressure;
    private TextView textViewHumidity;
    private TextView textViewSpeed;
    private TextView textViewDeg;
    private TextView textViewMaxtemp;
    private TextView textViewMintemp;
    private TextView textViewCity;
    private TextView textViewStatus;
    private Switch switchTemperatureUnit;

    private void performSearch(String cityName) {    // Méthode pour effectuer une recherche météorologique basée sur le nom de la ville

        // Création du service API et appel pour obtenir la météo actuelle de la ville.
        ApiService apiService = ApiClient.createService();
        Call<WeatherResponse> call = apiService.getCurrentWeather(cityName, API_KEY, "metric");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    // Mettre à jour l'interface utilisateur avec les données de la réponse
                    updateUI(response.body());
                } else {
                    // Gérer l'erreur
                }
            }
            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                // Gérer l'échec de la requête
            }
        });
    }

    private void updateUI(WeatherResponse weatherResponse) {     // Méthode pour mettre à jour l'interface utilisateur avec les données de la réponse météo.
        // Mettre à jour les TextViews avec les données de weatherResponse
        double temperature = weatherResponse.getMain().getTemp();
        double pressure = weatherResponse.getMain().getPressure();
        double humidity = weatherResponse.getMain().getHumidity();
        double speed = weatherResponse.getWind().getSpeed();
        double deg = weatherResponse.getWind().getDeg();
        double maxtemp = weatherResponse.getMain().getMaxtemp();
        double mintemp = weatherResponse.getMain().getMintemp();
        double temperatureFahrenheit = celsiusToFahrenheit(temperature);
        String  City = weatherResponse.getName();

        int roundedtemperature = (int) Math.round(temperature);
        int roundedTemperatureFahrenheit = (int) Math.round(temperatureFahrenheit);
        int roundedmaxtemp = (int) Math.round(maxtemp);
        int roundedmintemp = (int) Math.round(mintemp);

        // Extraction et affichage des données météo (température, pression, etc.) dans les TextViews.

        textViewTemperature.setText(String.valueOf(roundedtemperature + "°C"));
        textViewTemperatureF.setText(String.valueOf(roundedTemperatureFahrenheit + "°F"));
        textViewPressure.setText(String.valueOf(pressure + "hpa"));
        textViewHumidity.setText(String.valueOf(humidity + "%"));
        textViewSpeed.setText(String.valueOf(speed + "km/h"));
        textViewDeg.setText(String.valueOf(deg + "°"));
        textViewMaxtemp.setText(String.valueOf("Max Temp : " + roundedmaxtemp + "°C"));
        textViewMintemp.setText(String.valueOf("Min Temp : " + roundedmintemp + "°C"));
        textViewCity.setText(String.valueOf(City));
        textViewStatus = findViewById(R.id.textViewStatus);

        if (weatherResponse.getWeather().size() > 0) {
            String status = weatherResponse.getWeather().get(0).getDescription();
            textViewStatus.setText(status);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {     // Méthode onCreate appelée lors de la création de l'activité.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialisation de la TextView

        ApiService openWeatherMapService = ApiClient.createService();
        textViewTemperature = findViewById(R.id.textViewTemperature);
        textViewTemperatureF = findViewById(R.id.textViewTemperatureF);
        textViewPressure = findViewById(R.id.textViewPressure);
        textViewHumidity = findViewById(R.id.textViewHumidity);
        textViewSpeed = findViewById(R.id.textViewSpeed);
        textViewDeg = findViewById(R.id.textViewDeg);
        textViewMaxtemp = findViewById(R.id.textViewMaxtemp);
        textViewMintemp = findViewById(R.id.textViewMintemp);
        textViewCity = findViewById(R.id.City);

        // Configuration du SearchView pour rechercher les informations météorologiques.
        SearchView searchView = findViewById(R.id.SearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // Appeler la méthode pour effectuer la recherche
                performSearch(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                // Réagir aux changements de texte si nécessaire
                return false;
            }

        });

        // Création d'une instance du service
        ApiService apiService = ApiClient.createService();

        // Appel pour obtenir les informations météorologiques actuelles d'une ville (par exemple, "Paris")
        Call<WeatherResponse> call = apiService.getCurrentWeather("Dubai", "d0060546f0e76bb2f532096b8a62e825", "metric");

        // Exécution de la requête de manière asynchrone
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    // Traitement de la réponse réussie
                    WeatherResponse weatherResponse = response.body();

                    // Extrait les informations de MainData
                    double temperature = weatherResponse.getMain().getTemp();
                    double pressure = weatherResponse.getMain().getPressure();
                    double humidity = weatherResponse.getMain().getHumidity();
                    double speed = weatherResponse.getWind().getSpeed();
                    double deg = weatherResponse.getWind().getDeg();
                    double maxtemp = weatherResponse.getMain().getMaxtemp();
                    double mintemp = weatherResponse.getMain().getMintemp();
                    double temperatureFahrenheit = celsiusToFahrenheit(temperature);
                    String  City = weatherResponse.getName();

                    //permet d'arrondir les valeurs de la meteo
                    int roundedtemperature = (int) Math.round(temperature);
                    int roundedTemperatureFahrenheit = (int) Math.round(temperatureFahrenheit);
                    int roundedmaxtemp = (int) Math.round(maxtemp);
                    int roundedmintemp = (int) Math.round(mintemp);


                    // Affiche la température dans la TextView
                    textViewTemperature.setText(String.valueOf(roundedtemperature + "°C"));
                    textViewTemperatureF.setText(String.valueOf(roundedTemperatureFahrenheit + "°F"));
                    textViewPressure.setText(String.valueOf(pressure + "hpa"));
                    textViewHumidity.setText(String.valueOf(humidity + "%"));
                    textViewSpeed.setText(String.valueOf(speed + "km/h"));
                    textViewDeg.setText(String.valueOf(deg + "°"));
                    textViewMaxtemp.setText(String.valueOf("Max Temp : " + roundedmaxtemp + "°C"));
                    textViewMintemp.setText(String.valueOf("Min Temp : " + roundedmintemp + "°C"));
                    textViewCity.setText(String.valueOf(City));

                } else {
                    // Gestion des erreurs
                    // ...
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                // Gestion de l'échec de la requête
                // ...
            }
        });
    }

    public class VotreActivite extends AppCompatActivity {     // Classe interne pour gérer les unités de température (Celsius ou Fahrenheit).
        private boolean isCelsius = true; // Par défaut, afficher en Celsius
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }
    }
    private double celsiusToFahrenheit(double celsius) {
        return (celsius * 9/5) + 32;
    }     // Méthode pour convertir Celsius en Fahrenheit.

}