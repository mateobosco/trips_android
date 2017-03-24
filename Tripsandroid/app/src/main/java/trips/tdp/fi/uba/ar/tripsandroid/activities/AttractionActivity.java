package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import trips.tdp.fi.uba.ar.tripsandroid.BackEndClient;
import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.model.Attraction;

public class AttractionActivity extends AppCompatActivity {

    private Attraction attraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BackEndClient backEndClient = new BackEndClient();
        attraction = backEndClient.getAttraction();
        setTitle(attraction.getName());

        TextView t = (TextView)findViewById(R.id.attractionDescriptionTextView);
        t.setText(attraction.getDescription());
        t = (TextView)findViewById(R.id.attractionScheduleTimeTextView);
        t.setText(attraction.getSchedule());
        t = (TextView)findViewById(R.id.attractionAverageTimeTextView);
        t.setText(Integer.toString(attraction.getAverageTime()) + " minutos");
        t = (TextView)findViewById(R.id.attractionCostTextView);
        t.setText("$ " + Float.toString(attraction.getCost()));



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Marcado como favorito", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
