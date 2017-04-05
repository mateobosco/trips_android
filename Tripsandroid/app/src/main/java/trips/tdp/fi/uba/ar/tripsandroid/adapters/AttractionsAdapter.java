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
import trips.tdp.fi.uba.ar.tripsandroid.model.Attraction;

/**
 * Created by mbosco on 3/25/17.
 */

public class AttractionsAdapter extends RecyclerView.Adapter<AttractionsAdapter.ViewHolder> {

    private ArrayList<Attraction> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageView mImageView;
        public CardView mCardView;
        public final Context context;
        public ViewHolder(View v) {
            super(v);
            context = itemView.getContext();
            mTextView = (TextView) v.findViewById(R.id.attractionTextView);
            mImageView = (ImageView) v.findViewById(R.id.attractionImageView);

            mCardView = (CardView) v.findViewById(R.id.attractionCardView);

        }
    }

    public AttractionsAdapter(ArrayList<Attraction> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public AttractionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.attraction_card_layout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTextView.setText(mDataset.get(position).getName());
        
        Glide.with(holder.mImageView.getContext()).load(mDataset.get(position).getFullImageUrl(0))
                .into(holder.mImageView);

        final ViewHolder h = holder;

        holder.mCardView.setOnClickListener(new View.OnClickListener(){
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
