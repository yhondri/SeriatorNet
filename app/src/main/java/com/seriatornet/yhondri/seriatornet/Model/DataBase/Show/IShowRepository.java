package com.seriatornet.yhondri.seriatornet.Model.DataBase.Show;

/**
 * Created by yhondri on 26/03/2018.
 */

public interface IShowRepository {
    void createShow(Show show);

    Show getShowById(String id);

    void updateShow(Show show);

    void deleteShowById(String id);
}

