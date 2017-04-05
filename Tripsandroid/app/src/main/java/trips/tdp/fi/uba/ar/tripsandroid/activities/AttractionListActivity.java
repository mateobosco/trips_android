package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import trips.tdp.fi.uba.ar.tripsandroid.BackEndClient;
import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.adapters.AttractionsAdapter;
import trips.tdp.fi.uba.ar.tripsandroid.model.Attraction;
import trips.tdp.fi.uba.ar.tripsandroid.model.City;

public class AttractionListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Attraction> filteredModelList;
    private ArrayList<Attraction> attractions;
    private EditText searchEditBox;

    private City city;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.attractionListRecyclerView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();
        String cityJson = bundle.getString("cityJson");
        Gson gson = new Gson();
        city = gson.fromJson(cityJson, City.class);

        BackEndClient backEndClient = new BackEndClient();
        attractions = new ArrayList<Attraction>();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("exito", "Response is: " + response);
                try {

                    attractions = new ArrayList<Attraction>();
                    Gson gson = new Gson();
                    attractions = gson.fromJson(response, new TypeToken<ArrayList<Attraction>>(){}.getType());

                    filteredModelList = attractions;
                    mAdapter = new AttractionsAdapter(filteredModelList);
                    mRecyclerView.setAdapter(mAdapter);
                } catch (Exception e) {
                    Log.d("error", e.toString());
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("fracaso", "fracaso");
            }
        };

        backEndClient.getAttractions(city.getId(), this, responseListener, errorListener);

        setTitle("Atracciones de " + city.getName());

        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        filteredModelList = attractions;
        mAdapter = new AttractionsAdapter(filteredModelList);
        mRecyclerView.setAdapter(mAdapter);

        searchEditBox = (EditText) findViewById(R.id.searchAttractionListEditText);

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
                filteredModelList = filter(attractions, query);
                mAdapter = new AttractionsAdapter(filteredModelList);
                mRecyclerView.setAdapter(mAdapter);
            }

            private ArrayList<Attraction> filter(ArrayList<Attraction> attractions, String query) {
                ArrayList<Attraction> filtered = new ArrayList<>();
                for (Attraction a: attractions) {
                    if (a.getName().toLowerCase().contains(query.toLowerCase())){
                        filtered.add(a);
                    }
                }
                return filtered;
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {}
        });

    }
}
