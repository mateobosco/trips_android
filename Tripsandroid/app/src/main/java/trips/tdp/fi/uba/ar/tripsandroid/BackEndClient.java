package trips.tdp.fi.uba.ar.tripsandroid;

import android.content.Context;
import android.util.Log;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import trips.tdp.fi.uba.ar.tripsandroid.model.Attraction;
import trips.tdp.fi.uba.ar.tripsandroid.model.City;
import trips.tdp.fi.uba.ar.tripsandroid.model.Country;

/**
 * Created by joako on 19/3/17.
 */

public class BackEndClient {
    private static final String TAG = "BackEndClient";

    public static String baseUrl = "http://10.0.2.2:8080/TripsWebApp/";

    public void getCities(Context context, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url =this.baseUrl + "cities.json";
        Log.d("url", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        queue.add(stringRequest);
    }

    public void getAttraction(int id, Context context, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url =this.baseUrl + "attractions/" + id + ".json";
        Log.d("url", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        queue.add(stringRequest);
    }


    public void getCity(int cityId, Context context, Response.Listener<String> responseListener, Response.ErrorListener errorListener){

        RequestQueue queue = Volley.newRequestQueue(context);
        String url =this.baseUrl + "cities/" + cityId + ".json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        queue.add(stringRequest);

    }

    public void getAttractions(int cityId, Context context, Response.Listener<String> responseListener, Response.ErrorListener errorListener){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = this.baseUrl + "cities/" + cityId + "/attractions.json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        queue.add(stringRequest);
    }

    public static String getAttractionImageUrl(String imageUrl) {
        return BackEndClient.baseUrl + "images/attractions/" + imageUrl;
    }

    public static String getCityImageUrl(String imageUrl) {
        return BackEndClient.baseUrl + "images/cities/" + imageUrl;
    }
}
