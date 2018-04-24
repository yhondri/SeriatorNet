package com.seriatornet.yhondri.seriatornet.Module.ShowDetails;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;

import io.realm.Realm;

public class ShowDetailsInteractor implements ShowDetailsInteractorInput {

    private Realm realm;

    public ShowDetailsInteractor(Realm realm) {
        this.realm = realm;
    }

    @Override
    public Show getShowWithId(int showId) {
       return realm.where(Show.class).equalTo("id", showId).findFirst();
    }
}
