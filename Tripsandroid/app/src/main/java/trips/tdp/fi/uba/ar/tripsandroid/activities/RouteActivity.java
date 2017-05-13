package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.adapters.AttractionsInRouteAdapter;
import trips.tdp.fi.uba.ar.tripsandroid.adapters.CitiesAdapter;
import trips.tdp.fi.uba.ar.tripsandroid.model.Attraction;
import trips.tdp.fi.uba.ar.tripsandroid.model.Route;

public class RouteActivity extends AppCompatActivity {

    private Route route;

    private MapView mapView;
    private RecyclerView routeRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        Bundle bundle = getIntent().getExtras();
        final String routeJson = bundle.getString("routeJson");
        Gson gson = new Gson();
        route = gson.fromJson(routeJson, Route.class);

        setTitle(route.getName());

        mapView = (MapView) findViewById(R.id.routeMapView);
        routeRecyclerView = (RecyclerView) findViewById(R.id.routeRecyclerView);

        routeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        routeRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        routeRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AttractionsInRouteAdapter(route.getAttractions());
        routeRecyclerView.setAdapter(mAdapter);



        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                PolylineOptions polylineOptions = new PolylineOptions().geodesic(true);
                for(Attraction attraction: route.getAttractions()){
                    LatLng loc = new LatLng(attraction.getLatitude(), attraction.getLongitude());
                    polylineOptions.add(loc);

                    MarkerOptions marker = new MarkerOptions()
                            .title(attraction.getName())
                            .snippet(attraction.getSchedule())
                            .position(loc);
                    builder.include(marker.getPosition());
                    map.addMarker(marker);
                }
                map.addPolyline(polylineOptions);
                final LatLngBounds bounds = builder.build();
                final GoogleMap m = map;
                map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
                        m.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
