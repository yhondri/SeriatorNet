package com.seriatornet.yhondri.seriatornet.Module.Main.Interactor;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;
import com.seriatornet.yhondri.seriatornet.Module.Main.Presenter.ShowsPresentation;
import com.seriatornet.yhondri.seriatornet.Module.Main.Views.ShowsFragmentView;

import java.util.List;

public class ShowsPresenter implements ShowsPresentation {

    private ShowsInteractorInput interactor;
    private ShowsFragmentView view;

    public ShowsPresenter(ShowsInteractorInput interactor, ShowsFragmentView view) {
        this.interactor = interactor;
        this.view = view;
    }

    public List<Show> getShows() {
        return interactor.getShows();
    }
}
