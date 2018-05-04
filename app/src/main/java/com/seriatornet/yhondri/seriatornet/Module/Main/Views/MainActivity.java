package com.seriatornet.yhondri.seriatornet.Module.Main.Views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.seriatornet.yhondri.seriatornet.Model.APIKey;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Episode.Episode;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.MockDataManager;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Season.Season;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;
import com.seriatornet.yhondri.seriatornet.R;
import com.seriatornet.yhondri.seriatornet.Util.SharedPreferenceKey;
import com.seriatornet.yhondri.seriatornet.Util.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;

public class MainActivity extends AppCompatActivity {

    private static final String SELECTED_ITEM = "arg_selected_item";

    private int mSelectedItem;
    private BottomNavigationView mBottomNav;
    private OnBackPressedListener onBackPressedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadShows();
//                List<Show> shows = getAllShows();
//                loadSeasonsForShow(shows.get(0));
            }
        });

        selectFragment(R.id.action_watching);

        mBottomNav = findViewById(R.id.bottom_navigation);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectFragment(item.getItemId());
                return true;
            }
        });

//        if (!SharedPreferenceUtils.getInstance(this).getBoolanValue(SharedPreferenceKey.DID_LOAD_DEFAULT_DATA, false)) {
//            Realm realm = Realm.getDefaultInstance();
//            MockDataManager.parseShows(realm);
//            MockDataManager.parseEpisodes(realm);
//            realm.close();
//            SharedPreferenceUtils.getInstance(this).setValue(SharedPreferenceKey.DID_LOAD_DEFAULT_DATA, true);
//        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        onBackPressedListener.onBackPressed();
    }

    private void selectFragment(int itemId) {
        Fragment fragment = null;
        String toolBarTitle = null;
        switch (itemId) {
            case R.id.action_watching:
                fragment = UpcomingFragment.newInstance();
                toolBarTitle = getString(R.string.upcoming);
                break;
            case R.id.action_search:
                fragment = DiscoverFragment.newInstance();
                onBackPressedListener = (DiscoverFragment) fragment;
                toolBarTitle = getString(R.string.search);
                break;
            case R.id.action_show:
                fragment = ShowsFragment.newInstance();
                toolBarTitle = getString(R.string.shows);
                break;
            case R.id.action_profile:
                fragment = ProfileFragment.newInstance();
                toolBarTitle = getString(R.string.profile);
                break;
        }

        // update selected item
        mSelectedItem = itemId;

        updateToolbarText(toolBarTitle);

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment, fragment.getTag()).addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private void updateToolbarText(CharSequence text) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(text);
        }
    }

    private void loadShows() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("shows").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            saveShows(task);
                        } else {
                            Log.w("SERIATORNET", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void saveShows(Task<QuerySnapshot> task) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        for (QueryDocumentSnapshot document : task.getResult()) {
//            Log.d("SERIATORNET", document.getId() + " => " + document.getData());
            Map<String, Object> showMap = document.getData();
            String title = (String) showMap.get(APIKey.TITLE);
            String firebaseID = document.getId();
            String overview = (String) showMap.get(APIKey.OVERVIEW);
            String network = (String) showMap.get(APIKey.NETWORK);
            String language = (String) showMap.get(APIKey.LANGUAGE);
            String genres = (String) showMap.get(APIKey.GENRES);
            String country = (String) showMap.get(APIKey.COUNTRY);
            String posterPath = (String) showMap.get(APIKey.POSTER_PATH);
            String backdropPath = (String) showMap.get(APIKey.BACKDROP_PATH);
            Double raiting = (Double) showMap.get(APIKey.RATING);
            Double runtime = (Double) showMap.get(APIKey.RUNTIME);

            Show show = realm.createObject(Show.class, showMap.get(APIKey.TRAKT_ID));
            show.setFirebaseId(firebaseID);
            show.setTitle(title);
            show.setOverview(overview);
            show.setNetwork(network);
            show.setLanguage(language);
            show.setGenre(genres);
            show.setCountry(country);
            show.setPosterPath(posterPath);
            show.setBackdropPath(backdropPath);
            show.setRating(raiting);
            show.setRuntime(runtime.intValue());
        }

        getAllShows();

        realm.commitTransaction();
        realm.close();
    }

    public void getAllShows() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery query = realm.where(Show.class);
        List<Show> shows = realm.copyFromRealm(query.findAll());
        realm.close();

        for (Show show : shows) {
            loadSeasonsForShow(show);
        }

//        return shows;
    }


    private void loadSeasonsForShow(final Show show) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String path = "shows/" + show.getFirebaseId() + "/seasons";

        db.collection(path).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            saveSeasons(task, show);
                        } else {
                            Log.w("SERIATORNET", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void saveSeasons(Task<QuerySnapshot> task, Show show) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Show seasonShow = realm.where(Show.class).equalTo("id", show.getId()).findFirst();

        for (QueryDocumentSnapshot document : task.getResult()) {
            Map<String, Object> seasonMap = document.getData();
            String firebaseId = document.getId();
            long longNumber = (Long) seasonMap.get(APIKey.NUMBER);
            int number = (int) longNumber;
            longNumber = (Long) seasonMap.get(APIKey.TRAKT_ID);
            int traktId = (int) longNumber;

            Season season = realm.createObject(Season.class, seasonMap.get(APIKey.TRAKT_ID));
            season.setFirebaseId(firebaseId);
            season.setNumber(number);
            season.setTraktId(traktId);

            season.setShow(seasonShow);
        }

        realm.commitTransaction();
        realm.close();

        getAllSeasonsForShow(show);
    }

    public void getAllSeasonsForShow(Show show) {
        Realm realm = Realm.getDefaultInstance();
        List<Season> seasons = realm.where(Season.class).equalTo("show.id", show.getId()).findAll();
        realm.close();

        for (Season season : seasons) {
            loadEpisodesForSeason(season, show);
        }

    }

    private void loadEpisodesForSeason(final Season season, Show show) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String path = "shows/" + show.getFirebaseId() + "/seasons/" + season.getFirebaseId() + "/episodes";

        db.collection(path).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            saveEpisodesForSeason(task, season);
                        } else {
                            Log.w("SERIATORNET", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void saveEpisodesForSeason(Task<QuerySnapshot> task, Season season) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Season seasonEpisodes = realm.where(Season.class).equalTo("traktId", season.getTraktId()).findFirst();

        for (QueryDocumentSnapshot document : task.getResult()) {
            Map<String, Object> seasonMap = document.getData();

            String firebaseId = document.getId();
            long longNumber = (Long) seasonMap.get(APIKey.NUMBER);
            int number = (int) longNumber;
            String overView = (String) seasonMap.get(APIKey.OVERVIEW);
            String title = (String) seasonMap.get(APIKey.TITLE);

            Episode episode = realm.createObject(Episode.class, seasonMap.get(APIKey.TRAKT_ID));
            episode.setFirebaseId(Integer.parseInt(firebaseId));
            episode.setNumber(number);
            episode.setOverview(overView);
            episode.setTitle(title);

            episode.setSeason(seasonEpisodes);
        }

        realm.commitTransaction();
        realm.close();
    }
}
