package com.seriatornet.yhondri.seriatornet.Module.Main.Views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seriatornet.yhondri.seriatornet.R;

/**
 * Created by yhondri on 27/03/2018.
 */

public class ProfileFragment extends Fragment {

    private View rootView;


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

        TextView followingEpisodesTextView = rootView.findViewById(R.id.followingCounterTextView);
        TextView seenEpisodesTextView = rootView.findViewById(R.id.seenEpisodesTextView);
        TextView likesCounterTextView = rootView.findViewById(R.id.likesCounterTextView);

        ImageView profileImageView = rootView.findViewById(R.id.profileImageView);

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

    private void setUpView() {

    }
}
