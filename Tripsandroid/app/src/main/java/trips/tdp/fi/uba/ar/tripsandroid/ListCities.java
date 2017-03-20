package trips.tdp.fi.uba.ar.tripsandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

        String[] values = backEndClient.getCitiesNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        listView.setAdapter(adapter);


        listView.setOnItemClickListener(mMessageClickedHandler);

    }

}
