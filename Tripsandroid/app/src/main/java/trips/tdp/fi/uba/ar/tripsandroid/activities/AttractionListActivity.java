package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

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


        BackEndClient backEndClient = new BackEndClient();
        final City city = backEndClient.getCity();
        setTitle("Atracciones de " + city.getName());

        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        filteredModelList = city.getAttractions();
        mAdapter = new AttractionsAdapter(filteredModelList);
        mRecyclerView.setAdapter(mAdapter);

        EditText searchEditBox = (EditText) findViewById(R.id.searchAttractionListEditText);
        searchEditBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString();
                filteredModelList = filter(city.getAttractions(), query);
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
