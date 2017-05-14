package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.services.RegistrationService;

public class HomeActivity extends AppCompatActivity {
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logo = (ImageView) findViewById(R.id.homeLogo);

        logo.setAlpha(0.9f);

        Intent i = new Intent(this, RegistrationService.class);
        startService(i);

        CountDownTimer countDownTimer = new CountDownTimer(1500, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                Intent intent = new Intent(HomeActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        }.start();
    }
}
