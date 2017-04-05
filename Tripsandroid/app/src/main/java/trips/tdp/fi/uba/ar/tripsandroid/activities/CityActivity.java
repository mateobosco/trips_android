package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;

import trips.tdp.fi.uba.ar.tripsandroid.BackEndClient;
import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.model.City;

public class CityActivity extends AppCompatActivity {

    private City city;
    private ProgressBar spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        spinner = (ProgressBar) findViewById(R.id.progressBar1);

        Bundle bundle = getIntent().getExtras();
        String cityJson = bundle.getString("cityJson");
        Gson gson = new Gson();
        city = gson.fromJson(cityJson, City.class);


        setTitle(city.getName());

        ImageView cityToolbarImageView = (ImageView) findViewById(R.id.cityToolbarImageView);

        Log.d("imageurl", city.getFullImageUrl());
        Glide.with(this).load(city.getFullImageUrl())
                .into(cityToolbarImageView);


        BackEndClient client = new BackEndClient();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                spinner.setVisibility(View.GONE);

                CardView cardView = (CardView) findViewById(R.id.attractionsCardView);
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getApplicationContext(),AttractionListActivity.class);
                        Gson gson = new Gson();
                        String cityJson = gson.toJson(city);
                        i.putExtra("cityJson", cityJson);
                        startActivity(i);
                    }
                });

                Log.d("exito", "Response is: " + response);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("fracaso", "fracaso");
            }
        };

        client.getCity(city.getId(), this, responseListener, errorListener);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
