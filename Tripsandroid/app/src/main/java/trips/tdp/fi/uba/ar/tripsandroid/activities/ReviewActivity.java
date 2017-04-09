package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.adapters.ReviewsAdapter;
import trips.tdp.fi.uba.ar.tripsandroid.model.Review;

public class ReviewActivity extends AppCompatActivity {

    private ArrayList<ImageButton> yourRatingStars;
    private ArrayList<ImageView> ratingStars;
    private ReviewsAdapter mAdapter;

    private float rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        rating = (float) 2.5;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        setTitle("Reseñas del obelisco");

        RecyclerView r = (RecyclerView) findViewById(R.id.review_list);
        r.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Review> dataSet = new ArrayList<Review>();
        Review re = new Review();
        re.setText("asdf");
        re.setScore(2.0f);
        dataSet.add(re);
        mAdapter = new ReviewsAdapter(dataSet);
        r.setAdapter(mAdapter);








        TextView ratingInNumbers = (TextView) findViewById(R.id.rating_in_numbers);
        ratingInNumbers.setText(Float.toString(rating));


        ratingStars = new ArrayList<ImageView>();
        ratingStars.add(0,(ImageView) findViewById(R.id.rating_star_1));
        ratingStars.add(1,(ImageView) findViewById(R.id.rating_star_2));
        ratingStars.add(2,(ImageView) findViewById(R.id.rating_star_3));
        ratingStars.add(3,(ImageView) findViewById(R.id.rating_star_4));
        ratingStars.add(4,(ImageView) findViewById(R.id.rating_star_5));

        float newRating = rating * 10;
        for(int i = 0; i < ratingStars.size(); i++){
            if (newRating >= 10){
                ratingStars.get(i).setImageResource(R.drawable.star10);
            }else if (newRating <= 0){
                ratingStars.get(i).setImageResource(R.drawable.star00);
            }else if (newRating <= 1){
                ratingStars.get(i).setImageResource(R.drawable.star01);
            } else if (newRating <= 2){
                ratingStars.get(i).setImageResource(R.drawable.star02);
            } else if (newRating <= 3){
                ratingStars.get(i).setImageResource(R.drawable.star03);
            } else if (newRating <= 4){
                ratingStars.get(i).setImageResource(R.drawable.star04);
            } else if (newRating <= 5){
                ratingStars.get(i).setImageResource(R.drawable.star05);
            } else if (newRating <= 6){
                ratingStars.get(i).setImageResource(R.drawable.star06);
            } else if (newRating <= 7){
                ratingStars.get(i).setImageResource(R.drawable.star07);
            } else if (newRating <= 8){
                ratingStars.get(i).setImageResource(R.drawable.star08);
            } else if (newRating <= 9){
                ratingStars.get(i).setImageResource(R.drawable.star09);
            }
            newRating -= 10;
        }



    }





}
