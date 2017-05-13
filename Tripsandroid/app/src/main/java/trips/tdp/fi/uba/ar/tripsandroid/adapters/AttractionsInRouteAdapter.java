package trips.tdp.fi.uba.ar.tripsandroid.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
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
import trips.tdp.fi.uba.ar.tripsandroid.model.Attraction;

/**
 * Created by mbosco on 5/13/17.
 */

public class AttractionsInRouteAdapter extends RecyclerView.Adapter<AttractionsInRouteAdapter.ViewHolder> {

    private ArrayList<Attraction> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView attractionInRouteOrderTextView;
        public TextView attractionInRouteNameTextView;
        public TextView attractionInRouteDescriptionTextView;
        public ViewPager attractionInRouteViewPager;
        public CardView attractionInRouteCardView;
        public final Context context;
        public ViewHolder(View v) {
            super(v);
            context = itemView.getContext();
            attractionInRouteOrderTextView = (TextView) v.findViewById(R.id.attractionInRouteOrderTextView);
            attractionInRouteNameTextView = (TextView) v.findViewById(R.id.attractionInRouteNameTextView);
            attractionInRouteDescriptionTextView = (TextView) v.findViewById(R.id.attractionInRouteDescriptionTextView);
            attractionInRouteViewPager = (ViewPager) v.findViewById(R.id.attractionInRouteViewPager);
            attractionInRouteCardView = (CardView) v.findViewById(R.id.attractionInRouteCardView);

        }
    }

    public AttractionsInRouteAdapter(ArrayList<Attraction> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public AttractionsInRouteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.attraction_in_route_card_layout, parent, false);

        AttractionsInRouteAdapter.ViewHolder vh = new AttractionsInRouteAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AttractionsInRouteAdapter.ViewHolder holder, final int position) {
        holder.attractionInRouteOrderTextView.setText(Integer.toString(position + 1));
        holder.attractionInRouteNameTextView.setText(mDataset.get(position).getName());
        holder.attractionInRouteDescriptionTextView.setText(mDataset.get(position).getDescription());
        holder.attractionInRouteViewPager.setAdapter(new SlidingImageAdapter(holder.context, mDataset.get(position).getImagesFullPath()));

        final AttractionsInRouteAdapter.ViewHolder h = holder;
        holder.attractionInRouteCardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(h.context,AttractionActivity.class);

                Gson gson = new Gson();
                String attractionJson = gson.toJson(mDataset.get(position));
                i.putExtra("attractionJson", attractionJson);

                h.context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}

