package com.seriatornet.yhondri.seriatornet.Module.ShowDetails;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.seriatornet.yhondri.seriatornet.Model.AppKey;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;
import com.seriatornet.yhondri.seriatornet.R;
import com.seriatornet.yhondri.seriatornet.Util.Utils;

import org.w3c.dom.Text;

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

        int ratingValue = show.getRating().intValue()*10;
        ProgressBar scoreProgressBar = findViewById(R.id.scoreProgressBar);
        scoreProgressBar.setProgress(ratingValue);
        TextView textView = findViewById(R.id.ratingTextView);
        textView.setText(ratingValue +"%");

        likeImageButton = findViewById(R.id.likeImageButton);
        dislikeImageButton = findViewById(R.id.dislikeImageButton);
        followingButton = findViewById(R.id.followingButton);

        likeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreShow(true); }
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
            }
        });

        setUpLikeButtons();
        followShow(show.isFollowing());
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

        setUpLikeButtons();
    }

    private void setUpLikeButtons() {
        likeImageButton.setImageResource(show.isLike() ? R.drawable.ic_like_filled : R.drawable.ic_like_empty);
        dislikeImageButton.setImageResource(show.isDislike() ? R.drawable.ic_dislike_filled : R.drawable.ic_dislike_empty);
    }

    private void followShow(boolean isFollowing) {
        int backgroundColor;
        int textColor;

        if (isFollowing) {
            backgroundColor = getResources().getColor(R.color.following_show);
            textColor = getResources().getColor(R.color.white);
        } else {
            backgroundColor = getResources().getColor(R.color.white);
            textColor = getResources().getColor(R.color.following_show);
        }

        realm.beginTransaction();

        show.setFollowing(isFollowing);

        realm.commitTransaction();

        followingButton.setBackgroundColor(backgroundColor);
        followingButton.setTextColor(textColor);
    }
}
