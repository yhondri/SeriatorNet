package com.seriatornet.yhondri.seriatornet.Module.Main.Interactor;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.Episode.Episode;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ShowsInteractor implements ShowsInteractorInput {

    private Realm realm;

    public ShowsInteractor(Realm realm) {
        this.realm = realm;
    }

    @Override
    public List<Show> getShows() {
        RealmResults<Show> shows = realm.where(Show.class).findAll();
        return shows;
    }
}
