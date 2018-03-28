package com.seriatornet.yhondri.seriatornet.Main.Interactor;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.Episode.Episode;

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
        return realm.where(Episode.class).findAll();
    }
}
