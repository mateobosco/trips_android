package trips.tdp.fi.uba.ar.tripsandroid;

import android.content.Context;
import android.util.Log;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import trips.tdp.fi.uba.ar.tripsandroid.model.Attraction;
import trips.tdp.fi.uba.ar.tripsandroid.model.City;
import trips.tdp.fi.uba.ar.tripsandroid.model.Country;

/**
 * Created by joako on 19/3/17.
 */

public class BackEndClient {
    private static final String TAG = "BackEndClient";

    private String baseUrl;


    public BackEndClient() {
        this.baseUrl = new String("http://10.0.2.2:8080/TripsWebApp/");
    }


    public void getCities(Context context, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url =this.baseUrl + "cities.json";
        Log.d("url", url);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public Attraction getAttraction(){
        Attraction a = new Attraction("Torre Eiffel", "La Torre Eiffel es el símbolo de París, fue construida para la Exposición Universal de París de 1889 y actualmente es el monumento más visitado del mundo.");
        a.setCost(10);
        a.setLatitude(48.858333f);
        a.setLongitude(2.294444f);
        a.setAverageTime(120);
        a.setScheduleTime("Lu-Do 9:00 - 18:00");

        return a;
    }

    public City getCity(){
        Country country = new Country("Francia");
        City city = new City("Paris", country);
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
