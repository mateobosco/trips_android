package trips.tdp.fi.uba.ar.tripsandroid;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

import trips.tdp.fi.uba.ar.tripsandroid.model.Attraction;
import trips.tdp.fi.uba.ar.tripsandroid.model.City;

/**
 * Created by joako on 19/3/17.
 */

public class BackEndClient {

    private String baseUrl;


    public BackEndClient() {
        this.baseUrl = new String("localhost:8080/TripsWebApp/");
    }


    public String[] getCitiesNames(){

        URL url = null;
        HttpURLConnection urlConnection;
        try {
            url = new URL(this.baseUrl + "cities.json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        return new String[] {
                "Paris", "Roma", "Venecia", "Krakovia", "Mardel","Buenos Aires",
                "New York", "Londres", "Sao paulo", "Valencia","Madrid", "Barcelona"
        };
    }

    public Attraction getAttraction(){
        Attraction a = new Attraction("Torre Eiffel", "La Torre Eiffel es el símbolo de París, fue construida para la Exposición Universal de París de 1889 y actualmente es el monumento más visitado del mundo.");
        a.setCost(10);
        a.setLatitude(0.2f);
        a.setLongitude(2.3f);
        a.setAverageTime(120);
        a.setScheduleTime("Lu-Do 9:00 - 18:00");

        return a;
    }

    public City getCity(){
        City city = new City("Paris");
        city.setAttractions(this.getAttractions());
        return city;
    }

    public ArrayList<Attraction> getAttractions(){
        ArrayList<Attraction> attractions = new ArrayList<>();
        attractions.add(this.getAttraction());
        attractions.add(this.getAttraction());
        attractions.add(this.getAttraction());
        attractions.add(this.getAttraction());
        return attractions;
    }
}
