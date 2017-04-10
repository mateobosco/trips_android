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

import java.util.ArrayList;

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

    private float rating;

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


        setTitle("Reseñas del obelisco");


        Bundle bundle = getIntent().getExtras();
        String attractionJson = bundle.getString("attraction");
        Gson gson = new Gson();
        attraction = gson.fromJson(attractionJson, Attraction.class);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImageAdapter(ReviewActivity.this, attraction.getImages()));


        rating = 2.2f;
        RecyclerView r = (RecyclerView) findViewById(R.id.review_list);
        r.setLayoutManager(new LinearLayoutManager(this));


        RatingBar rB = (RatingBar) findViewById(R.id.ratingBar);

        rB.setRating(rating);
        rB.setStepSize(0.5f);

        ArrayList<Review> dataSet = new ArrayList<Review>();
        Review re = new Review();
        re.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla iaculis mauris sit amet diam fringilla mattis. Cras aliquet bibendum tortor, quis fringilla mi pellentesque nec. Duis pharetra ex id turpis efficitur ultricies. Etiam congue ullamcorper urna nec suscipit. Cras interdum mauris et lobortis semper. Etiam rutrum velit porttitor velit efficitur ornare. Integer eleifend vulputate dui, lobortis varius ex viverra at. Nulla facilisi. Phasellus blandit gravida ornare. ");
        re.setScore(2.0f);
        dataSet.add(re);
        dataSet.add(re);
        dataSet.add(re);
        dataSet.add(re);
        dataSet.add(re);
        dataSet.add(re);
        dataSet.add(re);
        dataSet.add(re);
        mAdapter = new ReviewsAdapter(dataSet);
        r.setAdapter(mAdapter);


        TextView ratingInNumbers = (TextView) findViewById(R.id.rating_in_numbers);
        ratingInNumbers.setText(Float.toString(rating));




    }


}
