package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Locale;

import trips.tdp.fi.uba.ar.tripsandroid.BackEndClient;
import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.adapters.AttractionsAdapter;
import trips.tdp.fi.uba.ar.tripsandroid.model.Attraction;
import trips.tdp.fi.uba.ar.tripsandroid.model.City;
import trips.tdp.fi.uba.ar.tripsandroid.model.Favourite;


public class AttractionListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Attraction> filteredModelList;
    private EditText searchEditBox;

    private City city;
    private boolean isFavourites;
    private ArrayList<Attraction> attractions;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Response.Listener<String> getAttractionsResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("exito", "Response is: " + response);
                try {
                    attractions = new ArrayList<>();
                    Gson gson = new Gson();
                    ArrayList<Attraction> allAttractions = gson.fromJson(response, new TypeToken<ArrayList<Attraction>>(){}.getType());
                    for (Attraction attraction: allAttractions){
                        if (attraction.getCity().getId() == city.getId()){
                            attractions.add(attraction);
                        }
                    }
                    filteredModelList = attractions;
                    mAdapter = new AttractionsAdapter(filteredModelList);
                    mRecyclerView.setAdapter(mAdapter);
                } catch (Exception e) {
                    Log.d("error", e.toString());
                }
            }
        };

        Response.Listener<String> getFavsResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("exito", "Response is: " + response);
                try {
                    attractions = new ArrayList<>();
                    Gson gson = new Gson();
                    ArrayList<Favourite> favs = gson.fromJson(response, new TypeToken<ArrayList<Favourite>>(){}.getType());
                    for (Favourite fav: favs){
                        if (fav.getAttraction().getCity().getId() == city.getId()){
                            attractions.add(fav.getAttraction());
                        }
                    }
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

        BackEndClient backEndClient = new BackEndClient();
        attractions = new ArrayList<>();

        String cityJson = getArguments().getString("cityJson");
        Gson gson = new Gson();
        city = gson.fromJson(cityJson, City.class);

        isFavourites = getArguments().getBoolean("isFavourites", false);
        if (!isFavourites){
            backEndClient.getAttractions(city.getId(), Locale.getDefault().getISO3Language(), this.getContext(), getAttractionsResponseListener, errorListener);
        }
        else{
            backEndClient.getFavourites(this.getContext(), getFavsResponseListener, errorListener);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attraction_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.tabAttractionListRecyclerView);
        searchEditBox = (EditText) view.findViewById(R.id.tabSearchAttractionListEditText);
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        filteredModelList = attractions;
        mAdapter = new AttractionsAdapter(filteredModelList);
        mRecyclerView.setAdapter(mAdapter);

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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
