package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.model.Advertisement;
import trips.tdp.fi.uba.ar.tripsandroid.model.media.Image;

public class AdvertisingActivity extends AppCompatActivity {

    Advertisement advertisement;

    protected void generateAdvertisement(){
        advertisement = new Advertisement();
        advertisement.setTitle("Una promocion de la concha de la lora");
        advertisement.setText("Esta promocion es muy buena porque podes comprar muchas porquerias que van a estar muy baratas, la promo es que tenes 2x1 en forros asi podes hacerte el que coges mucho con muchas minas pero en realidad los usas para paja deluxe");
        advertisement.setLatitude(-42.0f);
        advertisement.setLongitude(-42.0f);
        Image image = new Image();
        image.setPath("http://10.0.0.2:8080/TripsWebApp/images/cities/buenos_aires_city.png");

        advertisement.setImage(image);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertising);
        generateAdvertisement();

        setTitle(advertisement.getTitle());
    }
}
