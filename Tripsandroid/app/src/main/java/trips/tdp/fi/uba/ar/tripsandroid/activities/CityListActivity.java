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

public class CityListActivity extends AppCompatActivity {
    private BackEndClient backEndClient;
    private ProgressBar spinner;
    private EditText searchEditBox;
    private ListView listView;
    private ArrayList<String> cityNames;
    private ArrayList<String> displayableCityNames;


    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            Intent intent = new Intent(CityListActivity.this, AttractionListActivity.class);
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
                // Display the first 500 characters of the response string.
                try {
                    cityNames = new ArrayList<String>();
                    JSONArray arr = new JSONArray(response);
                    for (int i = 0; i < arr.length(); i++) {
                        String name = arr.getJSONObject(i).getString("name");
                        cityNames.add(name);
                    }

                    listView = (ListView) findViewById(R.id.list);
                    Collections.sort(cityNames);

                    displayableCityNames = cityNames;

                    final ArrayAdapter<String> a = new ArrayAdapter<String>(CityListActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, displayableCityNames);

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
                displayableCityNames = filter(cityNames, query);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CityListActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, displayableCityNames);
                listView.setAdapter(adapter);
            }

            private ArrayList<String> filter(ArrayList<String> cityNames, String query) {
                ArrayList<String> filtered = new ArrayList<>();
                for (String cityName : cityNames) {
                    if (cityName.toLowerCase().contains(query.toLowerCase())) {
                        filtered.add(cityName);
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
