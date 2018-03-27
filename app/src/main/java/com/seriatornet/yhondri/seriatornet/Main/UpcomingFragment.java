package com.seriatornet.yhondri.seriatornet.Main;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seriatornet.yhondri.seriatornet.Main.Adapter.UpcomingRecyclerViewAdapter;
import com.seriatornet.yhondri.seriatornet.R;

/**
 * Created by yhondri on 27/03/2018.
 */

public class UpcomingFragment extends Fragment {

    private String mText;
    private int mColor;

    private View rootView;

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

        RecyclerView recyclerView = rootView.findViewById(R.id.upcoming_recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        UpcomingRecyclerViewAdapter adapter = new UpcomingRecyclerViewAdapter(null);
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

}
