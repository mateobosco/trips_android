package trips.tdp.fi.uba.ar.tripsandroid.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.activities.AttractionActivity;
import trips.tdp.fi.uba.ar.tripsandroid.activities.AttractionsActivity;
import trips.tdp.fi.uba.ar.tripsandroid.activities.RouteActivity;
import trips.tdp.fi.uba.ar.tripsandroid.model.Route;

/**
 * Created by mbosco on 5/7/17.
 */

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.ViewHolder> {
    private ArrayList<Route> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final Context context;
        public TextView routeNameTextView;
        public TextView routeDescriptionTextView;
        public CardView routeCardView;
        public ViewPager routeViewPager;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            routeNameTextView = (TextView) itemView.findViewById(R.id.routeNameTextView);
            routeDescriptionTextView = (TextView) itemView.findViewById(R.id.routeDescriptionTextView);
            routeCardView = (CardView) itemView.findViewById(R.id.routeCardView);
            routeViewPager = (ViewPager) itemView.findViewById(R.id.routeViewPager);
        }

    }

    @Override
    public RoutesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.route_card_layout, parent, false);

        RoutesAdapter.ViewHolder vh = new RoutesAdapter.ViewHolder(v);
        return vh;
    }

    public RoutesAdapter(ArrayList<Route> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public void onBindViewHolder(RoutesAdapter.ViewHolder holder, final int position) {
        final RoutesAdapter.ViewHolder h = holder;
        holder.routeNameTextView.setText(mDataset.get(position).getName());
        holder.routeDescriptionTextView.setText(mDataset.get(position).getDescription());
        holder.routeViewPager.setAdapter(new SlidingImageAdapter(holder.context, mDataset.get(position).getAttractionImagesPath()));

        holder.routeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(h.context, RouteActivity.class);
                Gson gson = new Gson();
                String routeJson = gson.toJson(mDataset.get(position));
                i.putExtra("routeJson", routeJson);
                h.context.startActivity(i);
            }
        });

    }

    public int getItemCount() {
        return mDataset.size();
    }

}
