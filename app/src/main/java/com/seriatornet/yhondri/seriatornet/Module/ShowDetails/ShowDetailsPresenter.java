package com.seriatornet.yhondri.seriatornet.Module.ShowDetails;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;

public class ShowDetailsPresenter implements ShowDetailsPresentation {

    private ShowDetailsInteractorInput interactor;
    private ShowDetailsWireframe router;

    public ShowDetailsPresenter(ShowDetailsInteractorInput interactor, ShowDetailsWireframe router) {
        this.interactor = interactor;
        this.router = router;
    }

    @Override
    public Show getShowWithId(int showId) {
        return interactor.getShowWithId(showId);
    }

    @Override
    public void goToEpisodesList(int showId) {
        router.goToEpisodesList(showId);
    }
}
