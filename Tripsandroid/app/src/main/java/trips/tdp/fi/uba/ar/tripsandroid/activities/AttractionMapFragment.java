package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Locale;

import trips.tdp.fi.uba.ar.tripsandroid.BackEndClient;
import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.adapters.AttractionsAdapter;
import trips.tdp.fi.uba.ar.tripsandroid.model.Attraction;
import trips.tdp.fi.uba.ar.tripsandroid.model.City;

public class AttractionMapFragment extends Fragment implements GoogleMap.OnMarkerClickListener {

    private MapView mapView;
    private boolean isFavourites;

    private City city;
    private ArrayList<Attraction> attractions;

    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("exito", "Response is: " + response);
                try {
                    attractions = new ArrayList<>();
                    Gson gson = new Gson();
                    attractions = gson.fromJson(response, new TypeToken<ArrayList<Attraction>>(){}.getType());
                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap map) {
                            initializeMap(map);
                        }
                    });
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

        BackEndClient backEndClient = new BackEndClient();
        attractions = new ArrayList<>();

        isFavourites = getArguments().getBoolean("isFavourites", false);
        if (!isFavourites){
            String cityJson = getArguments().getString("cityJson");
            Gson gson = new Gson();
            city = gson.fromJson(cityJson, City.class);
            backEndClient.getAttractions(city.getId(), Locale.getDefault().getISO3Language(), this.getContext(), responseListener, errorListener);
        }
        else {
            backEndClient.getFavourites(this.getContext(), responseListener, errorListener);
        }

    }

    public void initializeMap(GoogleMap map){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Attraction attraction: attractions){
            LatLng loc = new LatLng(attraction.getLatitude(), attraction.getLongitude());
            MarkerOptions marker = new MarkerOptions()
                    .title(attraction.getName())
                    .snippet(attraction.getSchedule())
                    .position(loc);

            builder.include(marker.getPosition());
            map.addMarker(marker);
        }
        LatLngBounds bounds = builder.build();
        int padding = 100;
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        map.animateCamera(cu);
        map.setOnMarkerClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attraction_map, container, false);
        mapView = (MapView) view.findViewById(R.id.attractionsMapView);
        mapView.onCreate(savedInstanceState);
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String attractionName = marker.getTitle();
        for (Attraction a: attractions){
            if (a.getName().contains(attractionName)){
                Intent i = new Intent(this.getContext(), AttractionActivity.class);

                Gson gson = new Gson();
                String attractionJson = gson.toJson(a);
                i.putExtra("attractionJson", attractionJson);

                this.getContext().startActivity(i);
                return true;
            }
        }
        return false;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
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
