package trips.tdp.fi.uba.ar.tripsandroid.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;

import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.activities.AttractionActivity;
import trips.tdp.fi.uba.ar.tripsandroid.activities.PointOfInterestActivity;
import trips.tdp.fi.uba.ar.tripsandroid.model.Attraction;
import trips.tdp.fi.uba.ar.tripsandroid.model.PointOfInterest;

/**
 * Created by mbosco on 5/26/17.
 */

public class PointsOfInterestAdapter extends RecyclerView.Adapter<PointsOfInterestAdapter.ViewHolder> {
    private ArrayList<PointOfInterest> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView pointOfInterestTitleTextView;
        public ImageView pointOfInterestImageView;
        public CardView pointOfInterestCardView;
        public final Context context;
        public ViewHolder(View v) {
            super(v);
            context = itemView.getContext();
            pointOfInterestTitleTextView = (TextView) v.findViewById(R.id.pointOfInterestTitleTextView);
            pointOfInterestImageView = (ImageView) v.findViewById(R.id.pointOfInterestImageView);
            pointOfInterestCardView = (CardView) v.findViewById(R.id.pointOfInterestCardView);

        }
    }

    public PointsOfInterestAdapter(ArrayList<PointOfInterest> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public PointsOfInterestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.point_of_interest_card_layout, parent, false);

        PointsOfInterestAdapter.ViewHolder vh = new PointsOfInterestAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PointsOfInterestAdapter.ViewHolder holder, final int position) {
        holder.pointOfInterestTitleTextView.setText(mDataset.get(position).getName());

        /*Glide.with(holder.pointOfInterestImageView.getContext()).load(mDataset.get(position).getFullImageUrl(0))
                .into(holder.pointOfInterestImageView);*/

        final PointsOfInterestAdapter.ViewHolder h = holder;

        holder.pointOfInterestCardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(h.context, PointOfInterestActivity.class);

                Gson gson = new Gson();
                String pointOfInterestJson = gson.toJson(mDataset.get(position));
                i.putExtra("pointOfInterestJson", pointOfInterestJson);

                h.context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
