package com.seriatornet.yhondri.seriatornet.Module.ShowDetails;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.seriatornet.yhondri.seriatornet.Model.APIKey;
import com.seriatornet.yhondri.seriatornet.Model.AppKey;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;
import com.seriatornet.yhondri.seriatornet.R;
import com.seriatornet.yhondri.seriatornet.Util.SharedPreferenceKey;
import com.seriatornet.yhondri.seriatornet.Util.SharedPreferenceUtils;
import com.seriatornet.yhondri.seriatornet.Util.Utils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

public class ShowDetailsActivity extends AppCompatActivity {

    private Realm realm;
    private Show show;
    private ImageButton likeImageButton;
    private ImageButton dislikeImageButton;
    private ImageView showBackgroundImage;
    private Button followingButton;
    private DisplayImageOptions mOptionsThumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);

        realm = Realm.getDefaultInstance();

        ShowDetailsWireframe router = new ShowDetailsRouter(this);
        ShowDetailsInteractorInput interactor = new ShowDetailsInteractor(realm);
        final ShowDetailsPresentation presenter = new ShowDetailsPresenter(interactor, router);

        int showId = getIntent().getIntExtra(AppKey.SHOW_ID, 0);
        show = presenter.getShowWithId(showId);
        if (show != null) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(show.getTitle());
            }

            setUpView();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.goToEpisodesList(show.getId());
            }
        });
    }

    private void setUpView() {
        TextView channelNameTextView = findViewById(R.id.channelNameTextView);
        channelNameTextView.setText(show.getNetwork());
        TextView runTimeTextView = findViewById(R.id.runTimeTextView);
        runTimeTextView.setText(Integer.toString(show.getRuntime()));
        TextView genreTextView = findViewById(R.id.genreTextView);
        genreTextView.setText(show.getGenre());
        TextView showDescriptionTV = findViewById(R.id.showDescriptionTV);
        showDescriptionTV.setText(show.getOverview());
        showBackgroundImage = findViewById(R.id.showBackgroundImageView);

        if (!show.getBackdropPath().isEmpty()) {
            ImageLoader.getInstance().displayImage(show.getBackdropPath(), showBackgroundImage, mOptionsThumb);
        } else {
            showBackgroundImage.setImageResource(R.drawable.ic_television);
        }

        int ratingValue = show.getRating().intValue() * 10;
        ProgressBar scoreProgressBar = findViewById(R.id.scoreProgressBar);
        scoreProgressBar.setProgress(ratingValue);
        TextView textView = findViewById(R.id.ratingTextView);
        textView.setText(ratingValue + "%");

        likeImageButton = findViewById(R.id.likeImageButton);
        dislikeImageButton = findViewById(R.id.dislikeImageButton);
        followingButton = findViewById(R.id.followingButton);

        likeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreShow(true);

            }
        });

        dislikeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreShow(false);
            }
        });

        followingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followShow(!show.isFollowing());
                setUpFollowButtons(show.isFollowing());
            }
        });

        setUpLikeButtons();
        setUpFollowButtons(show.isFollowing());
    }

    private void scoreShow(boolean like) {
        if (show.isLike() && like) {
            return;
        }

        if (show.isDislike() && !like) {
            return;
        }

        realm.beginTransaction();

        show.setLike(like);
        show.setDislike(!like);

        realm.commitTransaction();


        postLikeShows();
        postDislikeShows();

        setUpLikeButtons();
    }

    private void setUpLikeButtons() {
        likeImageButton.setImageResource(show.isLike() ? R.drawable.ic_like_filled : R.drawable.ic_like_empty);
        dislikeImageButton.setImageResource(show.isDislike() ? R.drawable.ic_dislike_filled : R.drawable.ic_dislike_empty);
    }

    private void setUpFollowButtons(boolean isFollowing) {
        int backgroundColor;
        int textColor;

        if (isFollowing) {
            backgroundColor = getResources().getColor(R.color.following_show);
            textColor = getResources().getColor(R.color.white);
        } else {
            backgroundColor = getResources().getColor(R.color.white);
            textColor = getResources().getColor(R.color.following_show);
        }

        followingButton.setBackgroundColor(backgroundColor);
        followingButton.setTextColor(textColor);
    }

    private void followShow(boolean isFollowing) {

        realm.beginTransaction();

        show.setFollowing(isFollowing);

        realm.commitTransaction();

        postFollowingShows();
    }

    private void postFollowingShows() {
        String userName = SharedPreferenceUtils.getInstance(this).getStringValue(SharedPreferenceKey.USER_NAME, "");

        realm.beginTransaction();
        List<Show> followingShows = realm.where(Show.class).equalTo("following", true).findAll();
        realm.commitTransaction();

        List<String> ids = new ArrayList<>();

        for (int i = 0; i < followingShows.size(); i++) {
            ids.add(Integer.toString(followingShows.get(i).getId()));
        }

        HashMap<String, Object> followingShowsMap = new HashMap<>();
        followingShowsMap.put(APIKey.FOLLOWING, ids);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference followingUsersShowCollection = db.collection(APIKey.FOLLOWING_USERS_SHOW_COLLECTION);
        followingUsersShowCollection.document(userName).set(followingShowsMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                } else {

                }
            }
        });
    }

    private void postLikeShows() {
        String userName = SharedPreferenceUtils.getInstance(this).getStringValue(SharedPreferenceKey.USER_NAME, "");

        realm.beginTransaction();
        List<Show> likeShows = realm.where(Show.class).equalTo("like", true).findAll();
        realm.commitTransaction();

        List<String> ids = new ArrayList<>();

        for (int i = 0; i < likeShows.size(); i++) {
            ids.add(Integer.toString(likeShows.get(i).getId()));
        }

        HashMap<String, Object> followingShowsMap = new HashMap<>();
        followingShowsMap.put(APIKey.LIKE, ids);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference favoriteUsersShowCollection = db.collection(APIKey.FAVORITE_USERS_SHOW_COLLECTION);
        favoriteUsersShowCollection.document(userName).set(followingShowsMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                } else {

                }
            }
        });
    }

    private void postDislikeShows() {
        String userName = SharedPreferenceUtils.getInstance(this).getStringValue(SharedPreferenceKey.USER_NAME, "");

        realm.beginTransaction();
        List<Show> likeShows = realm.where(Show.class).equalTo("dislike", true).findAll();
        realm.commitTransaction();

        List<String> ids = new ArrayList<>();

        for (int i = 0; i < likeShows.size(); i++) {
            ids.add(Integer.toString(likeShows.get(i).getId()));
        }

        HashMap<String, Object> dislikeShowsMap = new HashMap<>();
        dislikeShowsMap.put(APIKey.DISLIKE, ids);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference NoFavoriteUsersShowCollection = db.collection(APIKey.NO_FAVORITE_USERS_SHOW_COLLECTION);
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
