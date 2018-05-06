package com.seriatornet.yhondri.seriatornet.Module.Main.Views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.seriatornet.yhondri.seriatornet.Model.APIKey;
import com.seriatornet.yhondri.seriatornet.Model.AppKey;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;
import com.seriatornet.yhondri.seriatornet.Module.Main.Adapter.ClickListener;
import com.seriatornet.yhondri.seriatornet.Module.Main.Adapter.DiscoverCategoriesAdapter;
import com.seriatornet.yhondri.seriatornet.Module.Main.Adapter.RecyclerTouchListener;
import com.seriatornet.yhondri.seriatornet.Module.Main.Adapter.ShowRecyclerViewAdapter;
import com.seriatornet.yhondri.seriatornet.Module.ShowDetails.ShowDetailsActivity;
import com.seriatornet.yhondri.seriatornet.R;
import com.seriatornet.yhondri.seriatornet.Util.SharedPreferenceKey;
import com.seriatornet.yhondri.seriatornet.Util.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.realm.Case;
import io.realm.Realm;

/**
 * Created by yhondri on 27/03/2018.
 */

public class DiscoverFragment extends Fragment implements ClickListener, SearchView.OnQueryTextListener, OnBackPressedListener {

    private View rootView;
    private ArrayAdapter<String> categoriesAdapter;
    private Realm realm;
    private RecyclerView recyclerView;
    private DiscoverCategoriesAdapter discoverCategoriesAdapter;
    private ShowRecyclerViewAdapter showRecyclerViewAdapter;

    public static Fragment newInstance() {
        Fragment frag = new DiscoverFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_discover, container, false);
        }

        realm = Realm.getDefaultInstance();

        recyclerView = rootView.findViewById(R.id.discover_recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerTouchListener recyclerTouchListener = new RecyclerTouchListener(getActivity(), recyclerView, this);
        recyclerView.addOnItemTouchListener(recyclerTouchListener);

        discoverCategoriesAdapter = new DiscoverCategoriesAdapter(getResources().getStringArray(R.array.shows_categories), getActivity());
        recyclerView.setAdapter(discoverCategoriesAdapter);

        showRecyclerViewAdapter = new ShowRecyclerViewAdapter(new ArrayList<Show>(), getActivity());

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

        // initialize views
//        mContent = view.findViewById(R.id.contentView);
//        mTextView = (TextView) view.findViewById(R.id.text);

        // set text and background color
//        mTextView.setText(mText);
//        mContent.setBackgroundColor(mColor);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar, menu);

        MenuItem mSearch = menu.findItem(R.id.action_search);

        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");
        mSearchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onBackPressed() {
        if (recyclerView.getAdapter().getClass() != DiscoverCategoriesAdapter.class) {
            recyclerView.setAdapter(discoverCategoriesAdapter);
        }
    }

    @Override
    public void onClick(View view, int position) {
        if (recyclerView.getAdapter().getClass() == DiscoverCategoriesAdapter.class) {
            String category = discoverCategoriesAdapter.getItemAt(position);

            List<Show> shows = realm.where(Show.class)
                    .contains("genre", category, Case.INSENSITIVE)
                    .sort(APIKey.TITLE)
                    .findAll();

            if (shows.size() > 0) {
                showRecyclerViewAdapter.refreshData(shows);
                recyclerView.setAdapter(showRecyclerViewAdapter);
            } else {
                Snackbar.make(view, getActivity().getString(R.string.No_shows_with_selected_genre), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();            }
        } else {

            Intent showsIntent = new Intent(getActivity(), ShowDetailsActivity.class);

            Show selectedShow = showRecyclerViewAdapter.getItemAt(position);
            showsIntent.putExtra(AppKey.SHOW_ID, selectedShow.getId());

            startActivity(showsIntent);
        }
    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.isEmpty()) {
            recyclerView.setAdapter(discoverCategoriesAdapter);
        } else {
            List<Show> shows = realm.where(Show.class)
                    .contains(APIKey.TITLE, newText, Case.INSENSITIVE)
                    .sort(APIKey.TITLE)
                    .findAll();

            showRecyclerViewAdapter.refreshData(shows);
            recyclerView.setAdapter(showRecyclerViewAdapter);
        }

        return false;
    }
}
