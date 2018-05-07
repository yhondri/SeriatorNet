package com.seriatornet.yhondri.seriatornet.Module.EpisodeActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.seriatornet.yhondri.seriatornet.Model.APIKey;
import com.seriatornet.yhondri.seriatornet.Model.AppKey;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Episode.Episode;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;
import com.seriatornet.yhondri.seriatornet.R;
import com.seriatornet.yhondri.seriatornet.Util.SharedPreferenceKey;
import com.seriatornet.yhondri.seriatornet.Util.SharedPreferenceUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;

public class EpisodeActivity extends AppCompatActivity {

    private Realm realm;
    private Show show;
    private Episode episode;
    private ImageView showBackgroundImage;
    private ImageView watchImageButton;
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

        watchImageButton = findViewById(R.id.watchImageButton);
        watchImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();

                episode.setWatched(!episode.isWatched());

                realm.commitTransaction();

                setUpWatchedButtons(episode.isWatched());
                postWatchedEpisode();
            }
        });

        if (episode.getEmissionDate() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("EE dd-MM-yyyy HH:mm");
            String emissionDate = formatter.format(episode.getEmissionDate());
            TextView emissionDateTextView = findViewById(R.id.emissionDateTextView);
            emissionDateTextView.setText(emissionDate);
        }

        setUpWatchedButtons(episode.isWatched());
    }

    private void setUpWatchedButtons(boolean isWatched) {
        Drawable watchedIcon;

        if (isWatched) {
            watchedIcon = getResources().getDrawable(R.drawable.ic_blue_eye);
        } else {
            watchedIcon = getResources().getDrawable(R.drawable.ic_white_eye);
        }

        watchImageButton.setImageDrawable(watchedIcon);
    }

    private void postWatchedEpisode() {
        String userName = SharedPreferenceUtils.getInstance(this).getStringValue(SharedPreferenceKey.USER_NAME, "");

        realm.beginTransaction();
        List<Episode> episodes = realm.where(Episode.class).equalTo("watched", true).findAll();
        realm.commitTransaction();

        List<String> ids = new ArrayList<>();

        for (int i = 0; i < episodes.size(); i++) {
            ids.add(Integer.toString(episodes.get(i).getId()));
        }

        HashMap<String, Object> dislikeShowsMap = new HashMap<>();
        dislikeShowsMap.put(APIKey.WATCHED, ids);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference NoFavoriteUsersShowCollection = db.collection(APIKey.WATCHED_USERS_SHOW_COLLECTION);
        NoFavoriteUsersShowCollection.document(userName).set(dislikeShowsMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                } else {

                }
            }
        });
    }

}
