package com.seriatornet.yhondri.seriatornet.Module.Main.Presenter;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;
import com.seriatornet.yhondri.seriatornet.Module.Main.Interactor.ShowsInteractorInput;
import com.seriatornet.yhondri.seriatornet.Module.Main.Views.ShowsFragmentView;

import java.util.List;

public interface ShowsPresentation {

  List<Show> getShows();

}
