package trips.tdp.fi.uba.ar.tripsandroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.model.Review;

/**
 * Created by joako on 9/4/17.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private ArrayList<Review> mDataset;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.review_card_layout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public ReviewsAdapter(ArrayList<Review> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.reviewText.setText(mDataset.get(position).getText());
//        holder.reviewScore.setText(Float.toString(mDataset.get(position).getScore()));
        holder.reviewScore.setText(Integer.toString((int)mDataset.get(position).getScore()));
        holder.reviewerName.setText(mDataset.get(position).getAuthor());
        String date = new SimpleDateFormat("yyyy MMM dd").format(mDataset.get(position).getDate());
        holder.reviewDate.setText(date);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView reviewText;
        public TextView reviewScore;
        public TextView reviewerName;
        public TextView reviewDate;
        public ViewHolder(View itemView) {
            super(itemView);

            reviewScore = (TextView) itemView.findViewById(R.id.textView4);
            reviewText= (TextView) itemView.findViewById(R.id.textView5);
            reviewerName = (TextView) itemView.findViewById(R.id.user_name);
            reviewDate = (TextView) itemView.findViewById(R.id.date);

        }
    }
}
