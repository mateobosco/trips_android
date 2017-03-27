package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

import trips.tdp.fi.uba.ar.tripsandroid.BackEndClient;
import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.model.Attraction;
import trips.tdp.fi.uba.ar.tripsandroid.model.City;
import trips.tdp.fi.uba.ar.tripsandroid.model.Country;

public class AttractionActivity extends AppCompatActivity {

    private Attraction attraction;
    private BackEndClient backEndClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        final TextView attractionDescriptionTextView = (TextView)findViewById(R.id.attractionDescriptionTextView);
        final TextView attractionScheduleTimeTextView = (TextView)findViewById(R.id.attractionScheduleTimeTextView);
        final TextView attractionAverageTimeTextView = (TextView)findViewById(R.id.attractionAverageTimeTextView);
        final TextView attractionCostTextView = (TextView)findViewById(R.id.attractionCostTextView);
        final MapView mapView = (MapView) findViewById(R.id.attractionMapView);
        final ImageView attractionActivityImageView = (ImageView) findViewById(R.id.attractionActivityImageView);
        mapView.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        final int attractionId = bundle.getInt("attractionId");
        final String attractionName = bundle.getString("attractionName");
        setTitle(attractionName);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String description = obj.getString("description");
                    attraction = new Attraction(attractionId, attractionName, description);
                    attraction.setAverageTime(obj.getInt("averageTime"));
                    attraction.setLatitude(BigDecimal.valueOf(obj.getDouble("latitude")).floatValue());
                    attraction.setLongitude(BigDecimal.valueOf(obj.getDouble("longitude")).floatValue());
                    attraction.setCost(BigDecimal.valueOf(obj.getDouble("cost")).floatValue());
                    attraction.setScheduleTime(obj.getString("schedule"));

                    if (obj.getJSONArray("images").length() > 0 ) {
                        String imageUrl = obj.getJSONArray("images").getJSONObject(0).getString("path");
                        attraction.addImage(imageUrl);
                    }
                    attractionDescriptionTextView.setText(attraction.getDescription());
                    attractionScheduleTimeTextView.setText(attraction.getSchedule());
                    attractionAverageTimeTextView.setText(Integer.toString(attraction.getAverageTime()) + " minutos");
                    attractionCostTextView.setText("$ " + Float.toString(attraction.getCost()));
                    Glide.with(AttractionActivity.this).load(attraction.getFullImage(0))
                            .into(attractionActivityImageView);
                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap map) {
                            LatLng loc = new LatLng(attraction.getLatitude(), attraction.getLongitude());
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 13));
                            map.addMarker(new MarkerOptions()
                                    .title(attraction.getName())
                                    .snippet(attraction.getSchedule())
                                    .position(loc));
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("exito", "Response is: " + response);
            }
        };


        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("fracaso", "fracaso");
            }
        };
        new BackEndClient().getAttraction(attractionId, this, responseListener, errorListener);






        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Marcado como favorito", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
