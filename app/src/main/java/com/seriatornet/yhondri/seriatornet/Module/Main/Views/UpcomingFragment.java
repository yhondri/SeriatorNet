package com.seriatornet.yhondri.seriatornet.Module.Main.Views;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seriatornet.yhondri.seriatornet.Module.Main.Adapter.UpcomingRecyclerViewAdapter;
import com.seriatornet.yhondri.seriatornet.Module.Main.Interactor.UpcomingInteractor;
import com.seriatornet.yhondri.seriatornet.Module.Main.Presenter.UpcomingPresentation;
import com.seriatornet.yhondri.seriatornet.Module.Main.Presenter.UpcomingPresenter;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Episode.Episode;
import com.seriatornet.yhondri.seriatornet.R;

import java.util.List;

import io.realm.Realm;

/**
 * Created by yhondri on 27/03/2018.
 */

public class UpcomingFragment extends Fragment implements UpcomingFragmentView {

    private String mText;
    private int mColor;

    private View rootView;
    private UpcomingPresentation presenter;
    private Realm realm;

    public static Fragment newInstance() {
        Fragment frag = new UpcomingFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_TEXT, text);
//        args.putInt(ARG_COLOR, color);
//        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_upcoming, container, false);
        }

        realm = Realm.getDefaultInstance();

        UpcomingInteractor upcomingInteractor = new UpcomingInteractor(realm);
        presenter = new UpcomingPresenter(upcomingInteractor, this);

        RecyclerView recyclerView = rootView.findViewById(R.id.upcoming_recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        List<Episode> episodes = presenter.getEpisodes();
        UpcomingRecyclerViewAdapter adapter = new UpcomingRecyclerViewAdapter(episodes, getActivity());
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // retrieve text and color from bundle or savedInstanceState
//        if (savedInstanceState == null) {
//            Bundle args = getArguments();
//            mText = args.getString(ARG_TEXT);
//            mColor = args.getInt(ARG_COLOR);
//        } else {
//            mText = savedInstanceState.getString(ARG_TEXT);
//            mColor = savedInstanceState.getInt(ARG_COLOR);
//        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        outState.putString(ARG_TEXT, mText);
//        outState.putInt(ARG_COLOR, mColor);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        presenter.onDestroy();
    }

    //region View implementation
    @Override
    public void releaseInstances() {
        realm.close();
    }

    //endregion
}
