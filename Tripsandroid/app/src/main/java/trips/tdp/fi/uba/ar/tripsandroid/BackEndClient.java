package trips.tdp.fi.uba.ar.tripsandroid;

import android.content.Context;
import android.util.Log;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import trips.tdp.fi.uba.ar.tripsandroid.activities.AttractionActivity;
import trips.tdp.fi.uba.ar.tripsandroid.model.Attraction;
import trips.tdp.fi.uba.ar.tripsandroid.model.Review;
import trips.tdp.fi.uba.ar.tripsandroid.model.User;

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

    public void getReviews(int attractionId, Context context, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = this.baseUrl + "attractions/" + attractionId + "/reviews.json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        queue.add(stringRequest);
    }

    public void sendReviews(final Review review, User user, Attraction attraction, Context context, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = this.baseUrl + "reviews/";
        final Review internalReview = review;
        final Attraction internalAttraction = attraction;
        final User internalUser = user;
        Log.d("url POST review", url);
        Log.d("review", review.toString());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, responseListener, errorListener)
        {
            @Override
            public byte[] getBody() throws com.android.volley.AuthFailureError {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T' hh:mm:ss'Z'");
                String date = sdf.format(new Date());

                String str = "{\"author_id\":" +
                        Integer.toString(internalUser.getId()) +
                        ",\"date\":\"" + date + "\"" +
                        ",\"score\":" + Integer.toString(review.getScore()) +
                        ",\"stop\":" + Integer.toString(internalAttraction.getId()) +
                        ",\"text\":\"" + review.getText() + "\"" +
                        "}";
                Log.d("review posting body", str);
                return str.getBytes();
            };

            @Override
            public Map<String, String> getHeaders(){
                Map<String, String>  headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        queue.add(stringRequest);
    }
}
