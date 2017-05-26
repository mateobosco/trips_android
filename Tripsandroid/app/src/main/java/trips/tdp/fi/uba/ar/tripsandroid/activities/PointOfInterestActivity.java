package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;

import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.model.PointOfInterest;

public class PointOfInterestActivity extends AppCompatActivity {

    private PointOfInterest pointOfInterest;

    private ImageView pointOfInterestImageView;
    private TextView pointOfInterestNameTextView;
    private TextView pointOfInterestDescriptionTextView;
    private LinearLayout pointOfInterestAudioguideLinearLayout;
    private Button pointOfInterestAudioguideButton;
    private SeekBar pointOfInterestAudioguideProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_of_interest);

        Bundle bundle = getIntent().getExtras();
        String pointOfInterestJson = bundle.getString("pointOfInterestJson");
        Gson gson = new Gson();
        pointOfInterest = gson.fromJson(pointOfInterestJson, PointOfInterest.class);
        setTitle("Punto de Interes");


        pointOfInterestImageView = (ImageView) findViewById(R.id.pointOfInterestImageView);
        pointOfInterestNameTextView = (TextView) findViewById(R.id.pointOfInterestNameTextView);
        pointOfInterestDescriptionTextView = (TextView) findViewById(R.id.pointOfInterestDescriptionTextView);
        pointOfInterestAudioguideLinearLayout = (LinearLayout) findViewById(R.id.pointOfInterestAudioguideLinearLayout);
        pointOfInterestAudioguideButton = (Button) findViewById(R.id.pointOfInterestAudioguideButton);
        pointOfInterestAudioguideProgressBar = (SeekBar) findViewById(R.id.pointOfInterestAudioguideProgressBar);


        pointOfInterestNameTextView.setText(pointOfInterest.getName());
        pointOfInterestDescriptionTextView.setText(pointOfInterest.getDescription());





    }


}
