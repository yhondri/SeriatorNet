package com.seriatornet.yhondri.seriatornet.Module.ShowDetails;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.seriatornet.yhondri.seriatornet.Model.AppKey;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;
import com.seriatornet.yhondri.seriatornet.R;

import org.w3c.dom.Text;

import io.realm.Realm;

public class ShowDetailsActivity extends AppCompatActivity {

    private Realm realm;
    private Show show;
    private ImageButton likeImageButton;
    private ImageButton dislikeImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();

        ShowDetailsInteractorInput interactor = new ShowDetailsInteractor(realm);
        ShowDetailsPresentation presenter = new ShowDetailsPresenter(interactor);

        int showId = getIntent().getIntExtra(AppKey.SHOW_ID, 0);
        show = presenter.getShowWithId(showId);
        if (show != null) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(show.getName());
            }

            setUpView();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setUpView() {
        TextView channelNameTextView = findViewById(R.id.channelNameTextView);
        channelNameTextView.setText("FOX");
        TextView runTimeTextView = findViewById(R.id.runTimeTextView);
        runTimeTextView.setText(Integer.toString(show.getRuntime()));
        TextView genreTextView = findViewById(R.id.genreTextView);
        genreTextView.setText(show.getGenre());
        TextView showDescriptionTV = findViewById(R.id.showDescriptionTV);
//        showDescriptionTV.setText(show.getDescription());

        ProgressBar scoreProgressBar = findViewById(R.id.scoreProgressBar);
        scoreProgressBar.setProgress(52);

        likeImageButton = findViewById(R.id.likeImageButton);
        dislikeImageButton = findViewById(R.id.dislikeImageButton);
        likeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeImageButton.setImageResource(R.drawable.ic_like_filled);
            }
        });

        dislikeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dislikeImageButton.setImageResource(R.drawable.ic_dislike_filled);
            }
        });
    }
}
