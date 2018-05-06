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
//                loadShows();
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
}
