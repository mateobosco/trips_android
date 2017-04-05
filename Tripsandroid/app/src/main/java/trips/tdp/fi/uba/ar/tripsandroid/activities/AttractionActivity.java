package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

import trips.tdp.fi.uba.ar.tripsandroid.BackEndClient;
import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.model.Attraction;
import trips.tdp.fi.uba.ar.tripsandroid.model.City;

public class AttractionActivity extends AppCompatActivity {

    private Attraction attraction;
    private BackEndClient backEndClient;
    private MapView mapView;


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
        mapView = (MapView) findViewById(R.id.attractionMapView);
        final ImageView attractionActivityImageView = (ImageView) findViewById(R.id.attractionActivityImageView);
        mapView.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        String cityJson = bundle.getString("attractionJson");
        Gson gson = new Gson();
        attraction = gson.fromJson(cityJson, Attraction.class);

        setTitle(attraction.getName());

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Gson gson = new Gson();
                    attraction = gson.fromJson(response, Attraction.class);

                    attractionDescriptionTextView.setText(attraction.getDescription());
                    attractionScheduleTimeTextView.setText(attraction.getSchedule());
                    attractionAverageTimeTextView.setText(Integer.toString(attraction.getAverageTime()) + " minutos");
                    attractionCostTextView.setText("$ " + Float.toString(attraction.getCost()));
                    Glide.with(AttractionActivity.this).load(attraction.getFullImageUrl(0))
                            .into(attractionActivityImageView);
                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap map) {
                            LatLng loc = new LatLng(attraction.getLatitude(), attraction.getLongitude());
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 14));
                            map.addMarker(new MarkerOptions()
                                    .title(attraction.getName())
                                    .snippet(attraction.getSchedule())
                                    .position(loc));
                        }
                    });

                } catch (Exception e) {
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
        new BackEndClient().getAttraction(attraction.getId(), this, responseListener, errorListener);




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
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
