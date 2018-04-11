package com.seriatornet.yhondri.seriatornet.Module.Main.Presenter;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.Episode.Episode;

import java.util.List;

/**
 * Created by yhondri on 28/03/2018.
 */

public interface UpcomingPresentation {
    /**
     * Método que se llama cuando la actividad se va a destruir.
     */
    void onDestroy();

    /**
     * Método que devuelve una lista de episódios.
     * @return una lista de episodios.
     */
    List<Episode> getEpisodes();
}
