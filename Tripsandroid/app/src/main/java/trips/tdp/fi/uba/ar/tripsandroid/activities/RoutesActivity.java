package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;

import trips.tdp.fi.uba.ar.tripsandroid.BackEndClient;
import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.adapters.CitiesAdapter;
import trips.tdp.fi.uba.ar.tripsandroid.adapters.RoutesAdapter;
import trips.tdp.fi.uba.ar.tripsandroid.model.City;
import trips.tdp.fi.uba.ar.tripsandroid.model.Route;

public class RoutesActivity extends AppCompatActivity {

    private RecyclerView routeListRecyclerView;
    private City city;
    private ArrayList<Route> routes;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        routeListRecyclerView = (RecyclerView) findViewById(R.id.routeListRecyclerView);

        setTitle(getResources().getString(R.string.routes));

        Bundle bundle = getIntent().getExtras();
        String cityJson = bundle.getString("cityJson");
        Gson gson = new Gson();
        city = gson.fromJson(cityJson, City.class);

        routeListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        routeListRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        routeListRecyclerView.setLayoutManager(mLayoutManager);

        routes = new ArrayList<Route>();
        mAdapter = new RoutesAdapter(routes);
        routeListRecyclerView.setAdapter(mAdapter);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    routes = new ArrayList<>();
                    Gson gson = new Gson();
                    routes = gson.fromJson(response, new TypeToken<ArrayList<Route>>(){}.getType());

                    mAdapter = new RoutesAdapter(routes);
                    routeListRecyclerView.setAdapter(mAdapter);

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

        BackEndClient backEndClient = new BackEndClient();
        backEndClient.getRoutes(city.getId(),this , responseListener, errorListener);
    }

}
