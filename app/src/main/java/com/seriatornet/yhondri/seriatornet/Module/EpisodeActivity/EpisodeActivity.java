package com.seriatornet.yhondri.seriatornet.Module.EpisodeActivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.seriatornet.yhondri.seriatornet.Model.AppKey;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Episode.Episode;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;
import com.seriatornet.yhondri.seriatornet.R;

import io.realm.Realm;

public class EpisodeActivity extends AppCompatActivity {

    private Realm realm;
    private Show show;
    private Episode episode;
    private ImageView showBackgroundImage;
    private DisplayImageOptions mOptionsThumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);

        realm = Realm.getDefaultInstance();

        int showId = getIntent().getIntExtra(AppKey.SHOW_ID, 0);
        int episodeId = getIntent().getIntExtra(AppKey.EPISODE_ID, 0);
        show = realm.where(Show.class)
                .equalTo("id", showId)
                .findFirst();

        episode = realm.where(Episode.class)
                .equalTo("id", episodeId)
                .findFirst();

        if (episode != null) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(episode.getTitle());
            }

            setUpView();
        }
    }

    private void setUpView() {
        TextView channelNameTextView = findViewById(R.id.channelNameTextView);
        channelNameTextView.setText(show.getNetwork());
        TextView runTimeTextView = findViewById(R.id.runTimeTextView);
        runTimeTextView.setText(Integer.toString(show.getRuntime()));
        TextView genreTextView = findViewById(R.id.genreTextView);
        genreTextView.setText(show.getGenre());
        TextView showDescriptionTV = findViewById(R.id.showDescriptionTV);
        showDescriptionTV.setText(episode.getOverview());
        showBackgroundImage = findViewById(R.id.showBackgroundImageView);

        if (!show.getBackdropPath().isEmpty()) {
            ImageLoader.getInstance().displayImage(show.getBackdropPath(), showBackgroundImage, mOptionsThumb);
        } else {
            showBackgroundImage.setImageResource(R.drawable.ic_television);
        }
    }

}
