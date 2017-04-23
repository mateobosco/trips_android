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


public class AttractionListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Attraction> filteredModelList;
    private EditText searchEditBox;

    private City city;
    private ArrayList<Attraction> attractions;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String cityJson = getArguments().getString("cityJson");
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

        backEndClient.getAttractions(city.getId(), Locale.getDefault().getDisplayLanguage(), this.getContext(), responseListener, errorListener);

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
