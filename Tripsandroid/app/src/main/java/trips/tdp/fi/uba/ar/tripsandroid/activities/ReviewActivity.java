package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.adapters.ReviewsAdapter;
import trips.tdp.fi.uba.ar.tripsandroid.adapters.SlidingImageAdapter;
import trips.tdp.fi.uba.ar.tripsandroid.model.Attraction;
import trips.tdp.fi.uba.ar.tripsandroid.model.Review;

public class ReviewActivity extends AppCompatActivity {

    private ArrayList<ImageButton> yourRatingStars;
    private ArrayList<ImageView> ratingStars;
    private ReviewsAdapter mAdapter;
    private ViewPager mPager;
    private Attraction attraction;
    private ArrayList<Review> reviews;
    private TextView reviewQuantityTextView;

    private float rating;
    private int reviewQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        setTitle("Reseñas");


        Bundle bundle = getIntent().getExtras();

        rating = Float.parseFloat(bundle.getString("reviewScoreAverage"));
        reviewQuantity = Integer.parseInt(bundle.getString("reviewQuantity"));
        reviewQuantityTextView = (TextView) findViewById(R.id.rating_quantity);
        reviewQuantityTextView.setText(Integer.toString(reviewQuantity));


        String attractionJson = bundle.getString("attraction");
        Gson gson = new Gson();
        attraction = gson.fromJson(attractionJson, Attraction.class);

        String reviewsJson = bundle.getString("reviews");
        Type listOfTestObject = new TypeToken<List<Review>>(){}.getType();
        reviews = gson.fromJson(reviewsJson, listOfTestObject);


        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImageAdapter(ReviewActivity.this, attraction.getImages()));


        RecyclerView r = (RecyclerView) findViewById(R.id.review_list);
        r.setLayoutManager(new LinearLayoutManager(this));


        RatingBar rB = (RatingBar) findViewById(R.id.ratingBar);

        rB.setRating(rating);
        rB.setStepSize(0.1f);

        mAdapter = new ReviewsAdapter(reviews);
        r.setAdapter(mAdapter);


        TextView ratingInNumbers = (TextView) findViewById(R.id.rating_in_numbers);
        ratingInNumbers.setText(Float.toString(rating) + " Reseñas");




    }


}
