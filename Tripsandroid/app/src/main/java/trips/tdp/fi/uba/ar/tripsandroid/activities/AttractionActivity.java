package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.Date;

import trips.tdp.fi.uba.ar.tripsandroid.BackEndClient;
import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.adapters.SlidingImageAdapter;
import trips.tdp.fi.uba.ar.tripsandroid.model.Attraction;
import trips.tdp.fi.uba.ar.tripsandroid.model.Review;
import trips.tdp.fi.uba.ar.tripsandroid.model.User;

public class AttractionActivity extends AppCompatActivity {

    private MapView mapView;
    private ViewPager mPager;
    private TextView attractionDescriptionTextView;
    private TextView attractionScheduleTimeTextView;
    private TextView attractionAverageTimeTextView;
    private TextView attractionCostTextView;
    private RatingBar reviewAverageRatingBar;
    private TextView reviewAverageTextView;
    private TextView reviewQuantityTextView;
    private LinearLayout newReviewLinearLayout;
    private EditText newReviewEditText;
    private RatingBar newReviewRatingBar;
    private Button sendReviewButton;
    private Button moreReviewsButton;
    private LinearLayout sendingReviewLoadingLinearLayout;

    private Response.Listener<String> responseListenerGetAttraction;
    private Response.Listener<String> responseListenerSendReview;
    private Response.ErrorListener errorListener;

    private Attraction attraction;

    private void initializeLayoutItems(){
        attractionDescriptionTextView = (TextView)findViewById(R.id.attractionDescriptionTextView);
        attractionScheduleTimeTextView = (TextView)findViewById(R.id.attractionScheduleTimeTextView);
        attractionAverageTimeTextView = (TextView)findViewById(R.id.attractionAverageTimeTextView);
        attractionCostTextView = (TextView)findViewById(R.id.attractionCostTextView);
        mapView = (MapView) findViewById(R.id.attractionMapView);
        reviewAverageRatingBar = (RatingBar) findViewById(R.id.reviewAverageRatingBar);
        reviewAverageTextView = (TextView) findViewById(R.id.reviewAverageTextView);
        reviewQuantityTextView = (TextView) findViewById(R.id.reviewQuantityTextView);
        newReviewLinearLayout = (LinearLayout) findViewById(R.id.newReviewLinearLayout);
        newReviewEditText = (EditText) findViewById(R.id.newReviewEditText);
        newReviewRatingBar = (RatingBar) findViewById(R.id.newReviewRatingBar);
        sendReviewButton = (Button) findViewById(R.id.sendReviewButton);
        moreReviewsButton = (Button) findViewById(R.id.moreReviewsButton);
        sendingReviewLoadingLinearLayout = (LinearLayout) findViewById(R.id.sendingReviewLoadingLinearLayout);
        sendingReviewLoadingLinearLayout.setVisibility(View.GONE);
        mPager = (ViewPager) findViewById(R.id.pager);

    }

    private void createResponseListeners(){
        responseListenerGetAttraction = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Gson gson = new Gson();
                    attraction = gson.fromJson(response, Attraction.class);

                    attractionDescriptionTextView.setText(attraction.getDescription());
                    attractionScheduleTimeTextView.setText(attraction.getSchedule());
                    attractionAverageTimeTextView.setText(Integer.toString(attraction.getAverageTime()) + " minutos");
                    attractionCostTextView.setText("$ " + Float.toString(attraction.getCost()));
                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap map) {
                            LatLng loc = new LatLng(attraction.getLatitude(), attraction.getLongitude());
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 14));
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

        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("fracaso", "fracaso");
            }
        };
    }

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

        initializeLayoutItems();

        mapView.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String cityJson = bundle.getString("attractionJson");
        Gson gson = new Gson();
        attraction = gson.fromJson(cityJson, Attraction.class);

        setTitle(attraction.getName());

        createResponseListeners();

        new BackEndClient().getAttraction(attraction.getId(), this, responseListenerGetAttraction, errorListener);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Marcado como favorito", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mPager.setAdapter(new SlidingImageAdapter(AttractionActivity.this, attraction.getImages()));

        sendReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Review newReview = new Review();
                newReview.setDate(new Date());
                newReview.setScore(newReviewRatingBar.getRating());
                newReview.setText(newReviewEditText.getText().toString());
                newReview.setAuthor(new User("anonimo"));

                newReviewLinearLayout.setVisibility(View.GONE);
                sendingReviewLoadingLinearLayout.setVisibility(View.VISIBLE);


            }
        });

        moreReviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttractionActivity.this, ReviewActivity.class);
                Gson gson = new Gson();
                String stringAttraction = gson.toJson(attraction);
                intent.putExtra("attraction", stringAttraction);
                startActivity(intent);
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
