package com.seriatornet.yhondri.seriatornet.Main.Views;

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
import com.seriatornet.yhondri.seriatornet.Model.APIKey;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.MockDataManager;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;
import com.seriatornet.yhondri.seriatornet.R;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmQuery;

public class MainActivity extends AppCompatActivity {

    private static final String SELECTED_ITEM = "arg_selected_item";

    private int mSelectedItem;
    private BottomNavigationView mBottomNav;

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

    /*   Realm realm = Realm.getDefaultInstance();
        MockDataManager.parseShows(realm);
        MockDataManager.parseEpisodes(realm);
        realm.close();
*///        Realm realm = Realm.getDefaultInstance();
//
//        MockDataManager.mockShow1s();
//
//        RealmResults<Show> shows = realm.where(Show.class).findAll();
//        Show simpsonsShow = shows.get(0);
//        MockDataManager.mockSeasons(simpsonsShow);
//
//        RealmResults<Season> seasons = realm.where(Season.class).findAll();
//        Season season1 = seasons.get(0);
//        MockDataManager.mockEpisodes(season1);
//
//
//        RealmResults<Episode> episodes = realm.where(Episode.class)
//                .equalTo("season.id", 1)
//                .findAll();
//
//        for (Episode episode : episodes) {
//            System.out.println("Name: " +episode.getTitle());
//        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {


    }

    private void selectFragment(int itemId) {
        Fragment fragment = null;
        // init corresponding fragment
        switch (itemId) {
            case R.id.action_watching:
                fragment = UpcomingFragment.newInstance();
                break;
            case R.id.action_search:
                fragment = DiscoverFragment.newInstance();

                break;
            case R.id.action_profile:
                fragment = ProfileFragment.newInstance();
                break;
        }

        // update selected item
        mSelectedItem = itemId;

        updateToolbarText("....");

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
            Log.d("SERIATORNET", document.getId() + " => " + document.getData());
            Map<String, Object> showMap = document.getData();
            Show show = realm.createObject(Show.class, showMap.get(APIKey.ID));
            String name = (String) showMap.get(APIKey.NAME);
            show.setName(name);
            show.setDescription((String) showMap.get(APIKey.DESCRIPTION));
            show.setCountry((String) showMap.get(APIKey.COUNTRY));
            show.setRuntime(((Long) showMap.get(APIKey.RUNTIME)).intValue());
            show.setLanguage((String) showMap.get(APIKey.LANGUAGE));
            show.setGenre((String) showMap.get(APIKey.GENRE));

        }

        realm.commitTransaction();
        realm.close();
        getAllShows();
    }

    public void getAllShows() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery query = realm.where(Show.class);
        List<Show> shows = realm.copyFromRealm(query.findAll());

        for (Show show : shows) {
            System.out.println("Show " + show.getName() + "  " + show.getRuntime() + " " + show.getLanguage());
        }

        realm.close();
    }
}
