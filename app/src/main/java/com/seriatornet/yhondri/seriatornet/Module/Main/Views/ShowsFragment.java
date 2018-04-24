package com.seriatornet.yhondri.seriatornet.Module.Main.Views;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;
import com.seriatornet.yhondri.seriatornet.Module.Main.Adapter.ShowRecyclerViewAdapter;
import com.seriatornet.yhondri.seriatornet.Module.Main.Interactor.ShowsInteractor;
import com.seriatornet.yhondri.seriatornet.Module.Main.Interactor.ShowsInteractorInput;
import com.seriatornet.yhondri.seriatornet.Module.Main.Interactor.ShowsPresenter;
import com.seriatornet.yhondri.seriatornet.Module.Main.Presenter.ShowsPresentation;
import com.seriatornet.yhondri.seriatornet.R;

import java.util.List;

import io.realm.Realm;

public class ShowsFragment extends Fragment implements ShowsFragmentView {

    private String mText;
    private int mColor;

    private View rootView;
    private ShowsPresentation presenter;
    private Realm realm;

    public static Fragment newInstance() {
        Fragment frag = new ShowsFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_shows, container, false);
        }

        realm = Realm.getDefaultInstance();

        ShowsInteractorInput upcomingInteractor = new ShowsInteractor(realm);
        presenter = new ShowsPresenter(upcomingInteractor, this);

        RecyclerView recyclerView = rootView.findViewById(R.id.shows_recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        List<Show> shows = presenter.getShows();
        ShowRecyclerViewAdapter adapter = new ShowRecyclerViewAdapter(shows, getActivity());
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
