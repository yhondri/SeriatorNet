package com.seriatornet.yhondri.seriatornet.Module.Main.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;
import com.seriatornet.yhondri.seriatornet.R;
import com.seriatornet.yhondri.seriatornet.Util.Utils;

import java.util.List;

public class ShowRecyclerViewAdapter extends RecyclerView.Adapter<ShowRecyclerViewAdapter.ViewHolder> {

    private List<Show> shows;
    private Context context;

    public ShowRecyclerViewAdapter(List<Show> shows, Context context) {
        this.shows = shows;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ShowRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_show, parent, false);
        ShowRecyclerViewAdapter.ViewHolder viewHolder = new ShowRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ShowRecyclerViewAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Show show = shows.get(position);

        holder.showNameTextView.setText(show.getName());
        holder.channelNameTextView.setText("FOX - Temp");
        holder.runTimeTextView.setText(Integer.toString(show.getRuntime()));
        holder.genreTextView.setText(show.getGenre());

        Drawable banner = Utils.getImage(show.getBanner(), context);
        Bitmap bannerBitmap = Utils.drawableToBitmap(banner);
        holder.backgroundImageView.setImageBitmap(bannerBitmap);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return shows.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView showNameTextView;
        TextView channelNameTextView;
        TextView runTimeTextView;
        TextView genreTextView;
        ImageView backgroundImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.upcoming_cardview);
            showNameTextView = itemView.findViewById(R.id.show_name_textview);
            channelNameTextView = itemView.findViewById(R.id.channelNameTextView);
            runTimeTextView = itemView.findViewById(R.id.runTimeTextView);
            genreTextView = itemView.findViewById(R.id.genreTextView);
            backgroundImageView = itemView.findViewById(R.id.show_background_imageview);
        }
    }
}
