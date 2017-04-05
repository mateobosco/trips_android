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
import org.json.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import trips.tdp.fi.uba.ar.tripsandroid.BackEndClient;

import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.model.City;
import trips.tdp.fi.uba.ar.tripsandroid.model.Country;

public class CityListActivity extends AppCompatActivity {
    private BackEndClient backEndClient;
    private ProgressBar spinner;
    private EditText searchEditBox;
    private ListView listView;
    private ArrayList<City> cities;
    private ArrayList<City> displayableCityNames;
    private Button myLocationButton;


    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            City city= (City)parent.getItemAtPosition(position);
            startCityActivity(city);
        }
    };

    private void startCityActivity(City city){
        Intent intent = new Intent(CityListActivity.this, CityActivity.class);
        intent.putExtra("cityName", city.getName());
        intent.putExtra("cityId", city.getId());
        intent.putExtra("cityImageUrl", city.getImageUrl());
        startActivity(intent);
    }

    public CityListActivity() {
        this.backEndClient = new BackEndClient();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);
        setTitle("Ciudades");

        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        listView = (ListView) findViewById(R.id.list);
        searchEditBox = (EditText) findViewById(R.id.search_box);
        myLocationButton = (Button) findViewById(R.id.myLocationButton);

        spinner.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);
        searchEditBox.setVisibility(View.INVISIBLE);
        askPermission();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, new String[]{});

        listView.setAdapter(adapter);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    cities = new ArrayList<City>();
                    JSONArray arr = new JSONArray(response);
                    for (int i = 0; i < arr.length(); i++) {
                        String name = arr.getJSONObject(i).getString("name");
                        String countryName = arr.getJSONObject(i).getString("country_name");
                        String imageUrl = arr.getJSONObject(i).getJSONObject("image").getString("path");
                        Country country = new Country(countryName);
                        int id = arr.getJSONObject(i).getInt("id");
                        City city = new City(id, name, country);
                        city.setImageUrl(imageUrl);
                        cities.add(city);
                    }

                    listView = (ListView) findViewById(R.id.list);
                    Collections.sort(cities);

                    displayableCityNames = cities;

                    final ArrayAdapter<City> a = new ArrayAdapter<City>(CityListActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, displayableCityNames);

                    listView.setAdapter(a);
                    spinner.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    searchEditBox.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
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


        listView.setOnItemClickListener(mMessageClickedHandler);

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
                displayableCityNames = filter(cities, query);
                ArrayAdapter<City> adapter = new ArrayAdapter<City>(CityListActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, displayableCityNames);
                listView.setAdapter(adapter);
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

        myLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                try{
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    Geocoder gcd = new Geocoder(getBaseContext(), Locale.ENGLISH);
                    List<Address> addresses = gcd.getFromLocation(latitude, longitude, 1);
                    String a = "";
                    String cityName = addresses.get(0).getLocality();
                    cityName = "Berlin";
                    matchCityName(cityName);
                }
                catch (SecurityException e){
                    e.printStackTrace();
                }
                catch (IOException e){
                    e.printStackTrace();
                }

            }
        });
    }

    private void matchCityName(String cityName){
        for (City c: displayableCityNames){
            if (c.getName().contains(cityName)){
                startCityActivity(c);
                return;
            }
        }
        //VER QUE AHCER SI NO MATCHEA NINGUNA

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
