package com.seriatornet.yhondri.seriatornet.Main.Interactor;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.Episode.Episode;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by yhondri on 28/03/2018.
 */

public class UpcomingInteractor implements UpcomingInteractorInput {

    private Realm realm;

    public UpcomingInteractor(Realm realm) {
        this.realm = realm;
    }

    @Override
    public List<Episode> getEpisodes() {
        RealmResults<Show> shows = realm.where(Show.class).findAll();
        List<Episode> episodes = new ArrayList<>();

        for (Show show : shows) {
            Episode episode = realm.where(Episode.class)
                    .equalTo("season.show.id", show.getId())
                    .sort("emissionDate")
                    .findFirst();
            episodes.add(episode);
        }

        return episodes;
    }
}
