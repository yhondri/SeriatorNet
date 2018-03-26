package com.seriatornet.yhondri.seriatornet.Main;

import android.icu.text.SymbolTable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.seriatornet.yhondri.seriatornet.Model.APIKey;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;
import com.seriatornet.yhondri.seriatornet.R;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                loadShows();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadShows() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("shows").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            saveShows(task);
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//
//
//                            Log.d("SERIATORNET", document.getId() + " => " + document.getData());
//                        }
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

        getAllShows();
    }

    public void getAllShows() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery query = realm.where(Show.class);
        List<Show> shows = realm.copyFromRealm(query.findAll());

        for (Show show : shows) {
            System.out.println("Show " +show.getName() + "  " +show.getRuntime() + " " + show.getLanguage());
        }
    }
}
