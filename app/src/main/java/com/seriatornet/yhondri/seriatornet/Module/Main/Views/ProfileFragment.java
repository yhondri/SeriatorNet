package com.seriatornet.yhondri.seriatornet.Module.Main.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.Episode.Episode;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;
import com.seriatornet.yhondri.seriatornet.Module.Login.LoginActivity;
import com.seriatornet.yhondri.seriatornet.R;
import com.seriatornet.yhondri.seriatornet.Util.FirebaseOauthService;
import com.seriatornet.yhondri.seriatornet.Util.SharedPreferenceKey;
import com.seriatornet.yhondri.seriatornet.Util.SharedPreferenceUtils;

import java.util.List;

import io.realm.Realm;

/**
 * Created by yhondri on 27/03/2018.
 */

public class ProfileFragment extends Fragment {

    private View rootView;
    private Realm realm;

    public static Fragment newInstance() {
        Fragment frag = new ProfileFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_TEXT, text);
//        args.putInt(ARG_COLOR, color);
//        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        }

        realm = Realm.getDefaultInstance();

        rootView.findViewById(R.id.logOutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        outState.putString(ARG_TEXT, mText);
//        outState.putInt(ARG_COLOR, mColor);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();

        setUp();
    }

    private void setUp() {
        TextView userEmailTextView = rootView.findViewById(R.id.userEmailTextView);
        userEmailTextView.setText(SharedPreferenceUtils.getInstance(getActivity()).getStringValue(SharedPreferenceKey.USER_NAME, ""));

        List<Show> followingShows = realm.where(Show.class)
                .equalTo("following", true)
                .findAll();

        TextView followingEpisodesTextView = rootView.findViewById(R.id.followingCounterTextView);
        followingEpisodesTextView.setText(Integer.toString(followingShows.size()));

        List<Show> likeShows = realm.where(Show.class)
                .equalTo("like", true)
                .findAll();
        TextView likesCounterTextView = rootView.findViewById(R.id.likesCounterTextView);
        likesCounterTextView.setText(Integer.toString(likeShows.size()));

        List<Show> dislikeShows = realm.where(Show.class)
                .equalTo("dislike", true)
                .findAll();
        TextView dislikeShowsTextView = rootView.findViewById(R.id.dislikeShowsTextView);
        dislikeShowsTextView.setText(Integer.toString(dislikeShows.size()));

        List<Episode> watchedEpisodes = realm.where(Episode.class)
                .equalTo("watched", true)
                .findAll();
        TextView watchedEpisodesTextView = rootView.findViewById(R.id.watchedEpisodesTextView);
        watchedEpisodesTextView.setText(Integer.toString(watchedEpisodes.size()));
    }

    private void logOut() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        realm.close();

        FirebaseOauthService firebaseOauthService = new FirebaseOauthService();
        firebaseOauthService.logOut();

        getActivity().finish();
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(loginIntent);
    }
}
