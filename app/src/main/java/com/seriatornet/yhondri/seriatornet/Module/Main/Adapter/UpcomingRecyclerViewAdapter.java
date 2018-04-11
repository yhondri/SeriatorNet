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

import com.seriatornet.yhondri.seriatornet.Model.DataBase.Episode.Episode;
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
    private Context context;

    public UpcomingRecyclerViewAdapter(List<Episode> episodes, Context context) {
        this.episodes = episodes;
        this.context = context;
        formatter = new SimpleDateFormat("EE dd-MM-yyyy HH:mm");
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
        String emissionDate = formatter.format(episode.getEmissionDate());
        holder.dateTextView.setText(emissionDate);

        Drawable posterDrawable = getImage(episode.getSeason().getShow().getPoster());
        Bitmap posterBitmap = drawableToBitmap(posterDrawable);
        holder.posterImageView.setImageBitmap(posterBitmap);

        Drawable banner = getImage(episode.getSeason().getShow().getBanner());
        Bitmap bannerBitmap = drawableToBitmap(banner);
        holder.backgroundImageView.setImageBitmap(bannerBitmap);

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

    /**
     * Convierte un drawable a bitmap adaptándolo a la densidad de píxeles del dispositivo.
     * @param drawable Drawable a convertir.
     * @return bitmap recibido.
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    /**
     * Obtiene una imagen a partir de su nombre.
     * @param name nombre de la imagen.
     * @return Drawabe obtenido.
     */
    private Drawable getImage(String name) {
        return context.getResources().getDrawable(context.getResources().getIdentifier(name, "drawable", context.getPackageName()));
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
