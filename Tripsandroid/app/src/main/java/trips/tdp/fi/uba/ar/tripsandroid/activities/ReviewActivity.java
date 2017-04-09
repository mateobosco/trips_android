package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import trips.tdp.fi.uba.ar.tripsandroid.R;

public class ReviewActivity extends AppCompatActivity {

    private ArrayList<ImageButton> yourRatingStars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        setTitle("Reseñas del obelisco");


        yourRatingStars = new ArrayList<ImageButton>();
        yourRatingStars.add(0,(ImageButton) findViewById(R.id.your_rating_star_1));
        yourRatingStars.add(1,(ImageButton) findViewById(R.id.your_rating_star_2));
        yourRatingStars.add(2,(ImageButton) findViewById(R.id.your_rating_star_3));
        yourRatingStars.add(3,(ImageButton) findViewById(R.id.your_rating_star_4));
        yourRatingStars.add(4,(ImageButton) findViewById(R.id.your_rating_star_5));

        for (int i = 0; i < yourRatingStars.size(); i++) {
            ImageButton star = yourRatingStars.get(i);
            final int index = i;
            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {

                    for (int i = 0; i < yourRatingStars.size(); i++){
                        if (i <= index){
                            yourRatingStars.get(i).setImageResource(R.drawable.star10);
                        }else{
                            yourRatingStars.get(i).setImageResource(R.drawable.star00);
                        }
                    }

                }
            });
        }

    }





}
