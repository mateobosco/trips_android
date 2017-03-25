package trips.tdp.fi.uba.ar.tripsandroid.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

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
        private final Context context;
        public ViewHolder(View v) {
            super(v);
            context = itemView.getContext();
            mTextView = (TextView) v.findViewById(R.id.info_text);

            ImageButton imageButton = (ImageButton) v.findViewById(R.id.attractionCardImageButton);
            imageButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context,AttractionActivity.class);
                    context.startActivity(i);
                }
            });
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDataset.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
