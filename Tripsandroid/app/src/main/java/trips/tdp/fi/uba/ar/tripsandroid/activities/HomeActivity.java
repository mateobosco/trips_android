package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import trips.tdp.fi.uba.ar.tripsandroid.ListAtractions;
import trips.tdp.fi.uba.ar.tripsandroid.ListCities;
import trips.tdp.fi.uba.ar.tripsandroid.R;

public class HomeActivity extends AppCompatActivity {
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logo = (ImageView) findViewById(R.id.homeLogo);

        logo.setAlpha(0.9f);

        CountDownTimer countDownTimer = new CountDownTimer(1500, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                Intent intent = new Intent(HomeActivity.this, ListCities.class);
                startActivity(intent);
            }
        }.start();
    }
}
