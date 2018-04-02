package com.seriatornet.yhondri.seriatornet.Module.Main.Presenter;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.Episode.Episode;

import java.util.List;

/**
 * Created by yhondri on 28/03/2018.
 */

public interface UpcomingPresentation {
    void onDestroy();
    List<Episode> getEpisodes();
}
