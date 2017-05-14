package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.model.Advertisement;
import trips.tdp.fi.uba.ar.tripsandroid.model.LoggedUser;
import trips.tdp.fi.uba.ar.tripsandroid.model.media.Image;

public class AdvertisingActivity extends AppCompatActivity {

    Advertisement advertisement;

    ImageView advertisementImageView;
    TextView advertisementSubtitleTextView;
    TextView advertisementDescriptionTextView;
    Button advertisementButton;
    MapView advertisementMapView;


    protected void generateAdvertisement(){
        advertisement = new Advertisement();
        advertisement.setTitle("Promocion");
        advertisement.setSubitle("Una promocion de la concha de la lora");
        advertisement.setDescription("Esta promocion es muy buena porque podes comprar muchas porquerias que van a estar muy baratas, la promo es que tenes 2x1 en forros asi podes hacerte el que coges mucho con muchas minas pero en realidad los usas para paja deluxe");
        advertisement.setLatitude(-42.0f);
        advertisement.setLongitude(-42.0f);
        advertisement.setLink("https://www.google.com.ar");
        Image image = new Image();
        image.setPath("http://10.0.2.2:8080/TripsWebApp/images/cities/buenos_aires_city.png");

        advertisement.setImage(image);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertising);
        Log.d("notif tracking","inside AdvertisingActivity onCreate");
        Bundle extras = getIntent().getExtras();
        Log.d("bundle extras", extras.getString("add_id"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        generateAdvertisement();

        setTitle(advertisement.getTitle());

        advertisementImageView = (ImageView) findViewById(R.id.advertisementImageView);
        advertisementSubtitleTextView = (TextView) findViewById(R.id.advertisementSubtitleTextView);
        advertisementDescriptionTextView = (TextView) findViewById(R.id.advertisementDescriptionTextView);
        advertisementButton = (Button) findViewById(R.id.advertisementButton);
        advertisementMapView = (MapView) findViewById(R.id.advertisementMapView);

        advertisementSubtitleTextView.setText(advertisement.getSubtitle());
        advertisementDescriptionTextView.setText(advertisement.getDescription());
        advertisementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(advertisement.getLink()));
                startActivity(i);
            }
        });


        Glide.with(this).load(advertisement.getImage().getPath()).into(advertisementImageView);

        advertisementMapView.onCreate(savedInstanceState);
        advertisementMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                LatLng loc = new LatLng(advertisement.getLatitude(), advertisement.getLongitude());
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 14));
                map.addMarker(new MarkerOptions()
                        .title(advertisement.getSubtitle())
                        .position(loc));
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        advertisementMapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        advertisementMapView.onPause();
    }

    @Override
    public void onResume() {
        advertisementMapView.onResume();
        super.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        advertisementMapView.onLowMemory();
    }
}
