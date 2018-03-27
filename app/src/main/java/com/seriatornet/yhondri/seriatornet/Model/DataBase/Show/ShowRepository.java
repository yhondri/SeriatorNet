package com.seriatornet.yhondri.seriatornet.Model.DataBase.Show;

import io.realm.Realm;

/**
 * Created by yhondri on 26/03/2018.
 */

public class ShowRepository implements IShowRepository {

    @Override
    public void createShow(Show show) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Show newShow = realm.createObject(Show.class, show.getId());
        newShow.setName(show.getName());
        newShow.setDescription(show.getDescription());
        newShow.setCountry(show.getCountry());
        newShow.setRuntime(show.getRuntime());
        newShow.setLanguage(show.getLanguage());
        newShow.setGenre(show.getGenre());

        realm.commitTransaction();
    }

    @Override
    public Show getShowById(String id) {
        return null;
    }

    @Override
    public void updateShow(Show show) {

    }

    @Override
    public void deleteShowById(String id) {

    }
}
