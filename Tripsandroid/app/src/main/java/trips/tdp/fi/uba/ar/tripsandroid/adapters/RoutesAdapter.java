package trips.tdp.fi.uba.ar.tripsandroid.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.model.Route;

/**
 * Created by mbosco on 5/7/17.
 */

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.ViewHolder> {
    private ArrayList<Route> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final Context context;
        public TextView cityNameTextView;
        public TextView cityCountryTextView;
        public CardView cityCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            cityNameTextView = (TextView) itemView.findViewById(R.id.cityNameTextView);
            cityCountryTextView = (TextView) itemView.findViewById(R.id.cityCountryTextView);
            cityCardView = (CardView) itemView.findViewById(R.id.cityCardView);
        }

    }

    @Override
    public RoutesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.city_card_layout, parent, false);

        RoutesAdapter.ViewHolder vh = new RoutesAdapter.ViewHolder(v);
        return vh;
    }

    public RoutesAdapter(ArrayList<Route> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public void onBindViewHolder(RoutesAdapter.ViewHolder holder, final int position) {
        final RoutesAdapter.ViewHolder h = holder;
        holder.cityNameTextView.setText(mDataset.get(position).getName());
        holder.cityCountryTextView.setText(mDataset.get(position).getDescription());
    }

    public int getItemCount() {
        return mDataset.size();
    }

}
