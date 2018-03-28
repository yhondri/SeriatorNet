package com.seriatornet.yhondri.seriatornet.Main.Presenter;

import com.seriatornet.yhondri.seriatornet.Main.Interactor.UpcomingInteractorInput;
import com.seriatornet.yhondri.seriatornet.Main.Views.UpcomingFragmentView;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Episode.Episode;

import java.util.List;

/**
 * Created by yhondri on 28/03/2018.
 */

public class UpcomingPresenter implements UpcomingPresentation {

    private UpcomingInteractorInput interactor;
    private  UpcomingFragmentView view;

    public UpcomingPresenter(UpcomingInteractorInput interactor, UpcomingFragmentView view) {
        this.interactor = interactor;
        this.view = view;
    }

    @Override
    public void onDestroy() {
        view.releaseInstances();
    }

    public List<Episode> getEpisodes() {
        return interactor.getEpisodes();
    }
}
