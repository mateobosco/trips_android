package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.json.*;

import java.util.ArrayList;
import java.util.Collections;

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


    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            City city= (City)parent.getItemAtPosition(position);
            Intent intent = new Intent(CityListActivity.this, CityActivity.class);
            intent.putExtra("cityName", city.getName());
            intent.putExtra("cityId", city.getId());
            intent.putExtra("cityImageUrl", city.getImageUrl());
            startActivity(intent);
        }
    };

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

        spinner.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);
        searchEditBox.setVisibility(View.INVISIBLE);


        String[] values = new String[0];
        values = new String[]{
        };

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


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

    }

}
