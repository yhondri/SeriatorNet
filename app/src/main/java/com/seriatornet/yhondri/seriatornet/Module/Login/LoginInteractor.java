package com.seriatornet.yhondri.seriatornet.Module.Login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.seriatornet.yhondri.seriatornet.Model.APIKey;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Episode.Episode;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Season.Season;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.User;
import com.seriatornet.yhondri.seriatornet.R;
import com.seriatornet.yhondri.seriatornet.Util.OauthService;
import com.seriatornet.yhondri.seriatornet.Util.OauthServiceResult;
import com.seriatornet.yhondri.seriatornet.Util.SharedPreferenceKey;
import com.seriatornet.yhondri.seriatornet.Util.SharedPreferenceUtils;
import com.seriatornet.yhondri.seriatornet.Util.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * Created by yhondri on 30/3/18.
 */

public class LoginInteractor implements LoginInteractorInput, OauthServiceResult {

    private Context context;
    private OauthService oauthService;
    private LoginInteractorOutput output;
    private String userName;
    private Show lastShow;
    private Realm realm;
    private SimpleDateFormat simpleDateFormat;

    public LoginInteractor(Context context, OauthService oauthService) {
        this.context = context;
        this.oauthService = oauthService;
        this.realm = Realm.getDefaultInstance();

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    }

    @Override
    public void onLogin(String email, String password) {

        if (Utils.isEmpty(email)) {
            output.onLoginDidFail(R.string.error_field_required);
            return;
        }

        if (!Utils.isEmailValid(email)) {
            output.onLoginDidFail(R.string.error_invalid_email);
            return;
        }

        if (Utils.isEmpty(password)) {
            output.onLoginDidFail(R.string.error_field_required);
            return;
        }

        oauthService.signInWithEmailAndPassword(email, password, this);
    }

    @Override
    public void onComplete(boolean success, User user) {
        if (success) {
            userDidLogin(user);

        } else {
            output.onLoginDidFail(R.string.login_did_fail);
        }
    }

    public void setOutput(LoginInteractorOutput output) {
        this.output = output;
    }

    public void userDidLogin(User newUser) {
        userName = newUser.getUserName();
        if (userName == null) {
            userName = newUser.getEmail();
        }

        SharedPreferenceUtils.getInstance(context).setValue(SharedPreferenceKey.USER_NAME, userName);
        SharedPreferenceUtils.getInstance(context).setValue(SharedPreferenceKey.IS_USER_LOGGED_IN, true);

        loadShows();
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
    }

    public void getAllShows() {
        RealmQuery query = realm.where(Show.class);
        List<Show> shows = realm.copyFromRealm(query.findAll());

        if (shows.size()  > 0) {
            lastShow = shows.get(shows.size() - 1);

            for (Show show : shows) {
                loadSeasonsForShow(show);
            }
        } else {
            output.onLoginDidFail(R.string.no_data);
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

        getAllSeasonsForShow(show);
    }

    public void getAllSeasonsForShow(Show show) {
        List<Season> seasons = realm.where(Season.class).equalTo("show.id", show.getId()).findAll();

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
        realm.beginTransaction();

        Season seasonEpisodes = realm.where(Season.class).equalTo("traktId", season.getTraktId()).findFirst();

        for (QueryDocumentSnapshot document : task.getResult()) {
            Map<String, Object> seasonMap = document.getData();

            String firebaseId = document.getId();
            long longNumber = (Long) seasonMap.get(APIKey.NUMBER);
            int number = (int) longNumber;
            String overView = (String) seasonMap.get(APIKey.OVERVIEW);
            String title = (String) seasonMap.get(APIKey.TITLE);
            String firstAired = (String) seasonMap.get(APIKey.FIRST_AIRED);

            Episode episode = realm.createObject(Episode.class, seasonMap.get(APIKey.TRAKT_ID));
            episode.setFirebaseId(Integer.parseInt(firebaseId));
            episode.setNumber(number);
            episode.setOverview(overView);
            episode.setTitle(title);

            if (firstAired != null && !firstAired.isEmpty()) {
                try {
                    episode.setEmissionDate(simpleDateFormat.parse(firstAired));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            episode.setSeason(seasonEpisodes);
        }

        realm.commitTransaction();

        if (season.getShow().getId() == lastShow.getId()) {
            loadFollowingShows();
        }
    }

    private void loadFollowingShows() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String path = APIKey.FOLLOWING_USERS_SHOW_COLLECTION + "/" + SharedPreferenceUtils.getInstance(context).getStringValue(SharedPreferenceKey.USER_NAME, "");

        db.document(path).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    updateFollowingShow(task);
                } else {
                    Log.w("SERIATORNET", "Error getting documents.", task.getException());
                }
            }
        });
    }

    public void updateFollowingShow(Task<DocumentSnapshot> task) {
        Map<String, Object> seasonMap = task.getResult().getData();

        if (seasonMap != null) {
            realm.beginTransaction();

            List<String> ids = (ArrayList<String>) seasonMap.get(APIKey.FOLLOWING);

            for (String id : ids) {
                Show show = realm.where(Show.class).equalTo("id", Integer.parseInt(id)).findFirst();
                if (show != null) {
                    show.setFollowing(true);
                }
            }

            realm.commitTransaction();
        }

        output.onLoadingDataDidFinish(userName);
    }
}
