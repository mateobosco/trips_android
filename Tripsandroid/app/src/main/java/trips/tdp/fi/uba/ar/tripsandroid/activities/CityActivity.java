package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import trips.tdp.fi.uba.ar.tripsandroid.BackEndClient;
import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.model.Attraction;
import trips.tdp.fi.uba.ar.tripsandroid.model.City;

public class CityActivity extends AppCompatActivity {

    private City city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        BackEndClient client = new BackEndClient();
        this.city = client.getCity();
        this.setTitle(city.getName());


        ImageButton imageButton = (ImageButton) findViewById(R.id.attractionsImageButton);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AttractionListActivity.class);
                startActivity(i);
            }
        });
    }
}
