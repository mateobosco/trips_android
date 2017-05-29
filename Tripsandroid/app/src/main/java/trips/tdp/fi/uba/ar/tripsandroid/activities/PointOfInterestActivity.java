package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;

import trips.tdp.fi.uba.ar.tripsandroid.BackEndClient;
import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.model.PointOfInterest;

public class PointOfInterestActivity extends AppCompatActivity {

    private PointOfInterest pointOfInterest;

    private ImageView pointOfInterestImageView;
    private TextView pointOfInterestNameTextView;
    private TextView pointOfInterestDescriptionTextView;
    private LinearLayout pointOfInterestAudioguideLinearLayout;
    private Button pointOfInterestAudioguideButton;
    private MediaPlayer mediaPlayer;
    private SeekBar pointOfInterestAudioguideProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_of_interest);

        Bundle bundle = getIntent().getExtras();
        String pointOfInterestJson = bundle.getString("pointOfInterestJson");
        Gson gson = new Gson();
        pointOfInterest = gson.fromJson(pointOfInterestJson, PointOfInterest.class);
        setTitle(getResources().getString(R.string.points_of_interest));


        pointOfInterestImageView = (ImageView) findViewById(R.id.pointOfInterestImageView);
        pointOfInterestNameTextView = (TextView) findViewById(R.id.pointOfInterestNameTextView);
        pointOfInterestDescriptionTextView = (TextView) findViewById(R.id.pointOfInterestDescriptionTextView);
        pointOfInterestAudioguideLinearLayout = (LinearLayout) findViewById(R.id.pointOfInterestAudioguideLinearLayout);
        pointOfInterestAudioguideButton = (Button) findViewById(R.id.pointOfInterestAudioguideButton);
        pointOfInterestAudioguideProgressBar = (SeekBar) findViewById(R.id.pointOfInterestAudioguideProgressBar);
        pointOfInterestAudioguideProgressBar.setVisibility(View.GONE);
        mediaPlayer = new MediaPlayer();



        pointOfInterestNameTextView.setText(pointOfInterest.getName());
        pointOfInterestDescriptionTextView.setText(pointOfInterest.getDescription());
        Glide.with(this).load(pointOfInterest.getFullImageUrl()).into(pointOfInterestImageView);
        if (!pointOfInterest.hasAudioguide()){
            pointOfInterestAudioguideLinearLayout.setVisibility(View.GONE);
        } else {
            String path = pointOfInterest.getAudioguide().getPath();
            String url = BackEndClient.getAudioUrl(path);
            try {
                mediaPlayer.setDataSource(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    pointOfInterestAudioguideButton.setEnabled(true);
                }
            });
            mediaPlayer.prepareAsync();

            pointOfInterestAudioguideButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                    } else {
                        mediaPlayer.start();
                    }
                }
            });
        }




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }


}
