package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.Manifest;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import trips.tdp.fi.uba.ar.tripsandroid.BackEndClient;

import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.adapters.AttractionsAdapter;
import trips.tdp.fi.uba.ar.tripsandroid.adapters.CitiesAdapter;
import trips.tdp.fi.uba.ar.tripsandroid.model.Attraction;
import trips.tdp.fi.uba.ar.tripsandroid.model.City;

public class CityListActivity extends AppCompatActivity {
    private BackEndClient backEndClient;
    private ProgressBar spinner;
    private EditText searchEditBox;
    private RecyclerView cityListRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<City> cities;
    private ArrayList<City> filteredModelList;
    private Button myLocationButton;


    private void startCityActivity(City city){
        Intent intent = new Intent(CityListActivity.this, CityActivity.class);

        Gson gson = new Gson();
        String cityJson = gson.toJson(city);
        intent.putExtra("cityJson", cityJson);

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);
        setTitle("Ciudades");

        this.backEndClient = new BackEndClient();
        cities = new ArrayList<>();

        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        searchEditBox = (EditText) findViewById(R.id.search_box);
        cityListRecyclerView = (RecyclerView) findViewById(R.id.cityListRecyclerView);

        spinner.setVisibility(View.VISIBLE);
        searchEditBox.setVisibility(View.INVISIBLE);
        askPermission();

        cityListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cityListRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this);
        cityListRecyclerView.setLayoutManager(mLayoutManager);

        filteredModelList = cities;
        mAdapter = new CitiesAdapter(filteredModelList);
        cityListRecyclerView.setAdapter(mAdapter);


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    cities = new ArrayList<>();
                    Gson gson = new Gson();
                    cities = gson.fromJson(response, new TypeToken<ArrayList<City>>(){}.getType());

                    Collections.sort(cities);

                    filteredModelList = cities;
                    mAdapter = new CitiesAdapter(filteredModelList);
                    cityListRecyclerView.setAdapter(mAdapter);


                    spinner.setVisibility(View.GONE);
                    searchEditBox.setVisibility(View.VISIBLE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("exito", "Response is: " + response);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("fracaso", "fracaso");
            }
        };

        backEndClient.getCities(this, responseListener, errorListener);



        searchEditBox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                searchEditBox.setText("");
            }
        });


        searchEditBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString();
                filteredModelList = filter(cities, query);
                mAdapter = new CitiesAdapter(filteredModelList);
                cityListRecyclerView.setAdapter(mAdapter);

            }

            private ArrayList<City> filter(ArrayList<City> cities, String query) {
                ArrayList<City> filtered = new ArrayList<>();
                for (City city : cities) {
                    if (city.getName().toLowerCase().contains(query.toLowerCase())) {
                        filtered.add(city);
                    }
                }
                return filtered;
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private void askPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("con permiso", "con permiso");
                } else {
                    Log.d("sin permiso", "sin permiso");
                    //VER QUE HACER SI NO TIENE PERMISO
                }
                return;
            }
        }
    }

}
