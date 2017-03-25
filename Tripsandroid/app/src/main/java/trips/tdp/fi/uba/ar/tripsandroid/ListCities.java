package trips.tdp.fi.uba.ar.tripsandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.json.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListCities extends AppCompatActivity {
    private BackEndClient backEndClient;

    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            Intent intent = new Intent(ListCities.this, ListAtractions.class);
            startActivity(intent);
        }
    };

    public ListCities() {
        this.backEndClient = new BackEndClient();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);

        ListView listView = (ListView) findViewById(R.id.list);

        String[] values = new String[0];
        values = new String[] {
                "Cargando las ciudades, aguarde"
        };

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        listView.setAdapter(adapter);



        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                try {
                    ArrayList<String> cityNames = new ArrayList<String>();
                    JSONArray arr = new JSONArray(response);
                    for (int i = 0; i < arr.length(); i++)
                    {
                        String name = arr.getJSONObject(i).getString("name");
                        cityNames.add(name);
                    }

                    ListView listView = (ListView) findViewById(R.id.list);

                    final ArrayAdapter<String> a = new ArrayAdapter<String>(ListCities.this, android.R.layout.simple_list_item_1, android.R.id.text1, cityNames);

                    listView.setAdapter(a);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("exito", "Response is: "+ response);
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

    }

}
