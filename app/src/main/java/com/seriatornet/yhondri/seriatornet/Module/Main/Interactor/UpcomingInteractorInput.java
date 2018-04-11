package com.seriatornet.yhondri.seriatornet.Module.Main.Interactor;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.Episode.Episode;

import java.util.List;

/**
 * Created by yhondri on 28/03/2018.
 */

public interface UpcomingInteractorInput {

    /**
     * Obitiene la lista de los episodios que le faltan por ver al usuario.
     * @return la lista de episodios.
     */
    List<Episode> getEpisodes();
}
