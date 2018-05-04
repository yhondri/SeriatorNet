package com.seriatornet.yhondri.seriatornet.Module.Episodes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.seriatornet.yhondri.seriatornet.Model.AppKey;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Episode.Episode;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Season.Season;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;
import com.seriatornet.yhondri.seriatornet.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class EpisodesActivity extends AppCompatActivity {

    private Show show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Realm realm = Realm.getDefaultInstance();

        int showId = getIntent().getIntExtra(AppKey.SHOW_ID, 0);
        show = realm.where(Show.class).equalTo("id", showId).findFirst();

        RecyclerView mRecyclerView = findViewById(R.id.episodes_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        List<Season> seasons = realm.where(Season.class).equalTo("show.id", showId).findAll();
        List<Episode> episodes = new ArrayList<>();

        List<SimpleSectionedRecyclerViewAdapter.Section> sections = new ArrayList<>();

        int counter = 0;
        for (Season season : seasons) {
            List<Episode> seasonsEpisodes = realm.where(Episode.class).equalTo("season.traktId", season.getTraktId()).findAll();
            episodes.addAll(seasonsEpisodes);

            sections.add(new SimpleSectionedRecyclerViewAdapter.Section(counter, "Temporada " + season.getNumber()));

            counter += seasonsEpisodes.size() + 1;
        }

        //Your RecyclerView.Adapter
        SimpleAdapter mAdapter = new SimpleAdapter(this, episodes);

        //Add your adapter to the sectionAdapter
        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new
                SimpleSectionedRecyclerViewAdapter(this,
                R.layout.content_section, R.id.section_text_view, mAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        //Apply this adapter to the RecyclerView
        mRecyclerView.setAdapter(mSectionedAdapter);
    }
}
