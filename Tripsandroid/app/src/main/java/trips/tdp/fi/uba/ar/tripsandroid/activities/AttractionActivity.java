package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.content.Intent;
import android.net.Uri;
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
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.ArrayList;
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
    private int reviewQuantity;
    private LinearLayout newReviewLinearLayout;
    private EditText newReviewEditText;
    private RatingBar newReviewRatingBar;
    private Button sendReviewButton;
    private Button moreReviewsButton;
    private Button moreReviewsButton2;
    private LinearLayout sendingReviewLoadingLinearLayout;
    private LinearLayout reviewSentLayout;
    private float reviewScoreAverage = 0f;
    private TextView attractionPhoneNumberTextView;

    private TextView reviewSubmittedText;

    private Response.Listener<String> responseListenerGetAttraction;
    private Response.Listener<String> responseListenerGetReviews;
    private Response.Listener<String> responseListenerSendReview;
    private Response.ErrorListener errorListener;
    private ArrayList<Review> reviews;

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
        moreReviewsButton2 = (Button) findViewById(R.id.moreReviewsButton2);
        sendingReviewLoadingLinearLayout = (LinearLayout) findViewById(R.id.sendingReviewLoadingLinearLayout);
        sendingReviewLoadingLinearLayout.setVisibility(View.GONE);
        mPager = (ViewPager) findViewById(R.id.pager);
        reviews = new ArrayList<Review>();
        reviewSubmittedText = (TextView) findViewById(R.id.reviewSubmitted);
        reviewSentLayout = (LinearLayout) findViewById(R.id.reviewSentLinearLayout);
        attractionPhoneNumberTextView = (TextView) findViewById(R.id.attractionPhoneNumberTextView);

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
                    attractionPhoneNumberTextView.setText(attraction.getTelephone());
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

        responseListenerGetReviews = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Gson gson = new Gson();
                    JSONArray arr = new JSONArray(response);
                    for( int i = 0; i < arr.length() ; i ++){
                        reviews.add(gson.fromJson(arr.getString(i),Review.class));
                    }
                    int sum = 0;
                    for (Review r : reviews){
                        sum+= r.getScore();
                    }
                    if (reviews.size() > 0){
                        reviewScoreAverage = sum * 1.0f / reviews.size();
                    }else{
                        reviewScoreAverage = 0;
                    }

                    reviewQuantity = reviews.size();
                    reviewAverageTextView.setText(String.format("%.1f", reviewScoreAverage));
                    reviewQuantityTextView.setText(Integer.toString(reviewQuantity) + " Reseñas");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("Reviews::exito", "Response is: " + response);
            }
        };

        responseListenerSendReview = new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
                Log.d("response to POST", "success");
                reviewSentLayout.setVisibility(View.VISIBLE);
                sendingReviewLoadingLinearLayout.setVisibility(View.GONE);

                Snackbar.make(findViewById(R.id.frame_layout), "Reseña enviada satisfactoriamente", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        };

        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("fracaso", error.toString());
            }
        };

        attractionPhoneNumberTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                String p = "tel:" + attraction.getTelephone();
                i.setData(Uri.parse(p));
                startActivity(i);
            }
        });
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
        new BackEndClient().getReviews(attraction.getId(), this, responseListenerGetReviews, errorListener);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Marcado como favorito", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mPager.setAdapter(new SlidingImageAdapter(AttractionActivity.this, attraction.getImages()));

        newReviewLinearLayout.setVisibility(View.VISIBLE);
        sendingReviewLoadingLinearLayout.setVisibility(View.GONE);

        sendReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Review newReview = new Review();
                newReview.setDate(new Date());
                newReview.setScore(newReviewRatingBar.getRating());
                newReview.setText(newReviewEditText.getText().toString());
                User user = new User("anonimo");
                newReview.setAuthor(user);

                newReviewLinearLayout.setVisibility(View.GONE);
                sendingReviewLoadingLinearLayout.setVisibility(View.VISIBLE);

                BackEndClient backEndClient = new BackEndClient();
                backEndClient.sendReviews(newReview, user, attraction, AttractionActivity.this, responseListenerSendReview, errorListener);

            }
        });

        moreReviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttractionActivity.this, ReviewActivity.class);
                Gson gson = new Gson();
                String stringAttraction = gson.toJson(attraction);
                intent.putExtra("attraction", stringAttraction);
                String stringReviews = gson.toJson(reviews);
                intent.putExtra("reviews", stringReviews);
                intent.putExtra("reviewScoreAverage", String.format("%.1f", reviewScoreAverage));
                intent.putExtra("reviewQuantity", Integer.toString(reviewQuantity));
                startActivity(intent);
            }
        });

        moreReviewsButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttractionActivity.this, ReviewActivity.class);
                Gson gson = new Gson();
                String stringAttraction = gson.toJson(attraction);
                intent.putExtra("attraction", stringAttraction);
                String stringReviews = gson.toJson(reviews);
                intent.putExtra("reviews", stringReviews);
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
