package com.seriatornet.yhondri.seriatornet.Main.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.Episode.Episode;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;
import com.seriatornet.yhondri.seriatornet.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by yhondri on 27/03/2018.
 */

public class UpcomingRecyclerViewAdapter extends RecyclerView.Adapter<UpcomingRecyclerViewAdapter.ViewHolder> {

    private List<Episode> episodes;
    private DateFormat formatter;
    public UpcomingRecyclerViewAdapter(List<Episode> episodes) {
        this.episodes = episodes;
        formatter = new SimpleDateFormat("EE MM-yyy HH:mm");
    }

    // Create new views (invoked by the layout manager)
    @Override
    public UpcomingRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_upcoming_show, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Episode episode = episodes.get(position);
        holder.showNameTextView.setText(episode.getSeason().getShow().getName());
        String episodeName = "S" + Integer.toString(episode.getSeason().getOrder()) + "E" +episode.getNumber();
        holder.episodeNameTextView.setText(episodeName);
        String emissionDate = formatter.format(episode.getEmissionDate());
        holder.dateTextView.setText(emissionDate);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return episodes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView showNameTextView;
        TextView episodeNameTextView;
        TextView dateTextView;
        ImageView posterImageView;
        ImageView backgroundImageView;

       public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.upcoming_cardview);
           showNameTextView = itemView.findViewById(R.id.show_name_textview);
           episodeNameTextView = itemView.findViewById(R.id.episode_name_textview);
           dateTextView = itemView.findViewById(R.id.upcoming_show_date_textview);
           posterImageView = itemView.findViewById(R.id.poster_imageview);
           backgroundImageView = itemView.findViewById(R.id.upcoming_show_background_imageview);
       }
    }
}
