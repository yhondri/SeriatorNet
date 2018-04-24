package com.seriatornet.yhondri.seriatornet.Module.ShowDetails;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;

public class ShowDetailsPresenter implements ShowDetailsPresentation {

    private ShowDetailsInteractorInput interactor;

    public ShowDetailsPresenter(ShowDetailsInteractorInput interactor) {
        this.interactor = interactor;
    }

    @Override
    public Show getShowWithId(int showId) {
        return interactor.getShowWithId(showId);
    }
}
