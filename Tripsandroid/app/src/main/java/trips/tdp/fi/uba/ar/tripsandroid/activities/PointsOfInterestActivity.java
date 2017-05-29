package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;


import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.adapters.PointsOfInterestAdapter;
import trips.tdp.fi.uba.ar.tripsandroid.model.Attraction;

public class PointsOfInterestActivity extends AppCompatActivity {

    private Attraction attraction;

    private ImageView attractionMapImageView;
    private RecyclerView pointsOfInterestRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_of_interest);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();
        String cityJson = bundle.getString("attractionJson");
        Gson gson = new Gson();
        attraction = gson.fromJson(cityJson, Attraction.class);
        setTitle(attraction.getName());

        attractionMapImageView = (ImageView) findViewById(R.id.attractionMapImageView);
        pointsOfInterestRecyclerView = (RecyclerView) findViewById(R.id.pointsOfInterestRecyclerView);

        Glide.with(this).load(attraction.getMapImage()).into(attractionMapImageView);


        pointsOfInterestRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pointsOfInterestRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        pointsOfInterestRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PointsOfInterestAdapter(attraction.getPointsOfInterest());
        pointsOfInterestRecyclerView.setAdapter(mAdapter);

    }
}
