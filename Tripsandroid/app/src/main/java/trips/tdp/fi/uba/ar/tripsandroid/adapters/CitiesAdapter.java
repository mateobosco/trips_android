package trips.tdp.fi.uba.ar.tripsandroid.adapters;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.activities.CityActivity;
import trips.tdp.fi.uba.ar.tripsandroid.activities.CityListActivity;
import trips.tdp.fi.uba.ar.tripsandroid.model.City;

/**
 * Created by mbosco on 4/9/17.
 */

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {
    private ArrayList<City> mDataset;
    private AlertDialog loadingDialog;


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView cityNameTextView;
        public TextView cityCountryTextView;
        public ImageView myLocationImageView;
        public CardView cityCardView;
        public final Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            cityNameTextView = (TextView) itemView.findViewById(R.id.cityNameTextView);
            cityCountryTextView = (TextView) itemView.findViewById(R.id.cityCountryTextView);
            myLocationImageView = (ImageView) itemView.findViewById(R.id.myLocationImageView);
            cityCardView = (CardView) itemView.findViewById(R.id.cityCardView);
        }
    }

    @Override
    public CitiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.city_card_layout, parent, false);

        CitiesAdapter.ViewHolder vh = new CitiesAdapter.ViewHolder(v);
        return vh;
    }

    public CitiesAdapter(ArrayList<City> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public void onBindViewHolder(CitiesAdapter.ViewHolder holder, final int position) {
        final CitiesAdapter.ViewHolder h = holder;
        if (position == 0){
            holder.cityCountryTextView.setVisibility(View.GONE);
            holder.cityNameTextView.setText("Mi ubicación");
            holder.cityCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createLoadingAlertDialog(h.context);
                    new GetCityName().execute(h.context);
                }
            });

        }
        else{
            holder.cityNameTextView.setText(mDataset.get(position-1).getName());
            holder.cityCountryTextView.setText(mDataset.get(position-1).getCountryName());
            holder.myLocationImageView.setVisibility(View.GONE);
            holder.cityCardView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    startCityActivity(h.context, mDataset.get(position-1));
                }
            });
        }

    }

    private void createLoadingAlertDialog(Context context){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Loading");
        View v = (View) LayoutInflater.from(context).inflate(R.layout.loading_alert_dialog_layout,null);
        alertDialogBuilder.setView(v);
        loadingDialog = alertDialogBuilder.show();
    }

    private void createTextAlertDialog(Context context, String text){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Error");
        alertDialogBuilder.setMessage(text);
        alertDialogBuilder.setPositiveButton("OK", null);
        alertDialogBuilder.show();
    }

    private void startCityActivity(Context context, City city){
        Intent i = new Intent(context,CityActivity.class);
        Gson gson = new Gson();
        String cityJson = gson.toJson(city);
        i.putExtra("cityJson", cityJson);

        context.startActivity(i);
    }

    private void matchCityName(String cityName, Context context){
        for (City c: mDataset){
            if (c.getName().contains(cityName)){
                startCityActivity(context, c);
            }
        }
        createTextAlertDialog(context, "La ciudad donde se encuentra no esta cargada");
    }

    private class GetCityName extends AsyncTask<Context, Void, String> {

        private Context c;
        @Override
        protected String doInBackground(Context... context) {
            c = context[0];
            try{
                LocationManager lm = (LocationManager) context[0].getSystemService(Context.LOCATION_SERVICE);
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                Geocoder gcd = new Geocoder(context[0], Locale.ENGLISH);
                List<Address> addresses = gcd.getFromLocation(latitude, longitude, 1);
                String cityName = addresses.get(0).getLocality();
                matchCityName(cityName, context[0]);
                return cityName;
            }

            catch (SecurityException e){
                e.printStackTrace();
                loadingDialog.cancel();
            }
            catch (IOException e){
                e.printStackTrace();
                loadingDialog.cancel();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            if (s == ""){
                createTextAlertDialog(c, "La API de google esta caida :(");
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size() + 1;
    }
}
