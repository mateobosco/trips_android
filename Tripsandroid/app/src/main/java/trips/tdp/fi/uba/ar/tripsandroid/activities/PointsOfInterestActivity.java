package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;

import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.adapters.CitiesAdapter;
import trips.tdp.fi.uba.ar.tripsandroid.adapters.PointsOfInterestAdapter;
import trips.tdp.fi.uba.ar.tripsandroid.model.Attraction;
import trips.tdp.fi.uba.ar.tripsandroid.model.PointOfInterest;
import trips.tdp.fi.uba.ar.tripsandroid.model.media.Audioguide;
import trips.tdp.fi.uba.ar.tripsandroid.model.media.Image;

public class PointsOfInterestActivity extends AppCompatActivity {

    private Attraction attraction;

    private ImageView attractionMapImageView;
    private RecyclerView pointsOfInterestRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Attraction createPointsOfInterest(Attraction a){
        PointOfInterest pof = new PointOfInterest();
        pof.setName("Cacona");
        pof.setDescription("Una verdadera cacona bien grande y enorme como le gusta a Juanma");
        Image i = new Image();
        i.setPath("");
        pof.setImage(i);
        Audioguide audio = new Audioguide();
        pof.setAudioguide(audio);
        ArrayList<PointOfInterest> points = new ArrayList<>();
        points.add(pof);
        a.setPointsOfInterests(points);
        return a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_of_interest);

        Bundle bundle = getIntent().getExtras();
        String cityJson = bundle.getString("attractionJson");
        Gson gson = new Gson();
        attraction = gson.fromJson(cityJson, Attraction.class);
        attraction = this.createPointsOfInterest(attraction);
        setTitle(attraction.getName());

        attractionMapImageView = (ImageView) findViewById(R.id.attractionMapImageView);
        pointsOfInterestRecyclerView = (RecyclerView) findViewById(R.id.pointsOfInterestRecyclerView);

        Glide.with(this).load(attraction.getFullImageUrl(0)).into(attractionMapImageView);


        pointsOfInterestRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pointsOfInterestRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        pointsOfInterestRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PointsOfInterestAdapter(attraction.getPointsOfInterests());
        pointsOfInterestRecyclerView.setAdapter(mAdapter);

    }
}
