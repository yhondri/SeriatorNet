package com.seriatornet.yhondri.seriatornet.Module.Main.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Episode.Episode;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;
import com.seriatornet.yhondri.seriatornet.R;
import com.seriatornet.yhondri.seriatornet.Util.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by yhondri on 27/03/2018.
 */

public class UpcomingRecyclerViewAdapter extends RecyclerView.Adapter<UpcomingRecyclerViewAdapter.ViewHolder> {

    private List<Episode> episodes;
    private DateFormat formatter;
    private Context context;
    private DisplayImageOptions mOptionsThumb;

    public UpcomingRecyclerViewAdapter(List<Episode> episodes, Context context) {
        this.episodes = episodes;
        this.context = context;
        formatter = new SimpleDateFormat("EE dd-MM-yyyy HH:mm");

        mOptionsThumb = Utils.getDisplayImageOptionsBuildWithDisplayer(R.drawable.ic_television,
                300,
                new FadeInBitmapDisplayer(300));

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .build();
        ImageLoader.getInstance().init(config);
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
        holder.showNameTextView.setText(episode.getSeason().getShow().getTitle());
        String emissionDate = formatter.format(episode.getEmissionDate());
        holder.dateTextView.setText(emissionDate);

        Show show = episode.getSeason().getShow();
        if (!show.getBackdropPath().isEmpty()) {
            ImageLoader.getInstance().displayImage(show.getBackdropPath(), holder.backgroundImageView, mOptionsThumb);
        } else {
            holder.backgroundImageView.setImageResource(R.drawable.ic_television);
        }

        if (!show.getPosterPath().isEmpty()) {
            ImageLoader.getInstance().displayImage(show.getPosterPath(), holder.posterImageView, mOptionsThumb);
        } else {
            holder.posterImageView.setImageResource(R.drawable.ic_television);
        }

        String episodeName = "S";

        if (episode.getSeason().getNumber() < 10) {
            episodeName += "0" + Integer.toString(episode.getSeason().getNumber());
        } else {
            episodeName += Integer.toString(episode.getSeason().getNumber());
        }

        if (episode.getNumber() < 10) {
            episodeName += "E0" + Integer.toString(episode.getNumber());
        } else {
            episodeName += "E" + Integer.toString(episode.getNumber());
        }

        holder.episodeNameTextView.setText(episodeName);
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
