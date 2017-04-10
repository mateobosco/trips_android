package trips.tdp.fi.uba.ar.tripsandroid.adapters;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import trips.tdp.fi.uba.ar.tripsandroid.activities.AttractionActivity;
import trips.tdp.fi.uba.ar.tripsandroid.activities.CityActivity;
import trips.tdp.fi.uba.ar.tripsandroid.activities.CityListActivity;
import trips.tdp.fi.uba.ar.tripsandroid.model.City;

/**
 * Created by mbosco on 4/9/17.
 */

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {
    private ArrayList<City> mDataset;

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
            holder.cityNameTextView.setText("Mi Ubicación");
            holder.cityCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocationManager lm = (LocationManager) h.context.getSystemService(Context.LOCATION_SERVICE);
                    try{
                        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        double longitude = location.getLongitude();
                        double latitude = location.getLatitude();
                        Geocoder gcd = new Geocoder(h.context, Locale.ENGLISH);
                        List<Address> addresses = gcd.getFromLocation(latitude, longitude, 1);
                        String cityName = addresses.get(0).getLocality();
                        cityName = "Berlin";
                        matchCityName(cityName, h.context);
                    }
                    catch (SecurityException e){
                        e.printStackTrace();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
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
        //VER QUE AHCER SI NO MATCHEA NINGUNA

    }

    @Override
    public int getItemCount() {
        return mDataset.size() + 1;
    }
}
