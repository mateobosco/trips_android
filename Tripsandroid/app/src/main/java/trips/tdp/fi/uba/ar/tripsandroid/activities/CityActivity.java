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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import trips.tdp.fi.uba.ar.tripsandroid.BackEndClient;
import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.model.City;
import trips.tdp.fi.uba.ar.tripsandroid.model.Favourite;

public class CityActivity extends AppCompatActivity {

    private City city;
    private CardView attractionsCardView;
    private CardView favouritesCardView;
    private CardView routesCardView;


    private void initializeActivity(){
        attractionsCardView = (CardView) findViewById(R.id.attractionsCardView);
        attractionsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),AttractionsActivity.class);
                Gson gson = new Gson();
                String cityJson = gson.toJson(city);
                i.putExtra("cityJson", cityJson);
                i.putExtra("isFavourites", false);
                startActivity(i);
            }
        });

        favouritesCardView = (CardView) findViewById(R.id.favouritesCardView);
        favouritesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),AttractionsActivity.class);
                Gson gson = new Gson();
                String cityJson = gson.toJson(city);
                i.putExtra("cityJson", cityJson);
                i.putExtra("isFavourites", true);
                startActivity(i);
            }
        });

        routesCardView = (CardView) findViewById(R.id.routesCardView);
        routesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RoutesActivity.class);
                Gson gson = new Gson();
                String cityJson = gson.toJson(city);
                i.putExtra("cityJson", cityJson);
                startActivity(i);
            }
        });
    }


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

        Bundle bundle = getIntent().getExtras();
        String cityJson = bundle.getString("cityJson");
        Gson gson = new Gson();
        city = gson.fromJson(cityJson, City.class);
        initializeActivity();


        setTitle(city.getName());

        ImageView cityToolbarImageView = (ImageView) findViewById(R.id.cityToolbarImageView);

        Glide.with(this).load(city.getFullImageUrl())
                .into(cityToolbarImageView);


        BackEndClient client = new BackEndClient();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Gson gson = new Gson();
                    boolean showFavouritesCard = false;
                    ArrayList<Favourite> favs = gson.fromJson(response, new TypeToken<ArrayList<Favourite>>(){}.getType());
                    for (Favourite fav: favs){
                        if (fav.getAttraction().getCity().getId() == city.getId()){
                            showFavouritesCard = true;
                            break;
                        }
                    }
                    if (showFavouritesCard) {
                        favouritesCardView.setVisibility(View.VISIBLE);
                    }
                    Log.d("exito", "Response is: " + response);
                }
                catch (Exception e){
                    Log.d("error", "error parsing response");
                }

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("fracaso", "fracaso");
            }
        };

        client.getFavourites(this, responseListener, errorListener);

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
