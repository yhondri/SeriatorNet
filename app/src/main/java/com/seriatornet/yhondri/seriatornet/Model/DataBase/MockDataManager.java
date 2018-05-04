package com.seriatornet.yhondri.seriatornet.Model.DataBase;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.seriatornet.yhondri.seriatornet.ApplicationController;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Episode.Episode;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Season.Season;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;
import com.seriatornet.yhondri.seriatornet.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import io.realm.Realm;

/**
 * Created by yhondri on 28/03/2018.
 */

public class MockDataManager {

    // Private Methods *********************************************************************************
    private static String fileGetContents(Context c, int resource) {
        InputStream in = c.getResources().openRawResource(resource);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder s = new StringBuilder();
        String l;
        try {
            while ((l = reader.readLine()) != null) {
                s.append(l);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s.toString();
    }

    public static void parseShows(Realm realm) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        String json = fileGetContents(ApplicationController.getInstance(), R.raw.shows);
        Type listType = new TypeToken<List<Show>>() {
        }.getType();

        List<Show> shows = gson.fromJson(json.toString(), listType);
        realm.beginTransaction();

        for (Show show : shows) {
            Show newShow = realm.createObject(Show.class, show.getId());
            newShow.setTitle(show.getTitle());
            newShow.setCountry(show.getCountry());
            newShow.setRuntime(show.getRuntime());
            newShow.setLanguage(show.getLanguage());
            newShow.setGenre(show.getGenre());
//            newShow.setPoster(show.getPoster());
//            newShow.setBanner(show.getBanner());
//            newShow.setDescription(show.getDescription());
        }

        realm.commitTransaction();
    }

    public static void parseEpisodes(Realm realm) {
        int[] episodeIds = {R.raw.thesimpsons_episodes, R.raw.mrrobot_episodes,
                R.raw.bigmouth_episodes, R.raw.gameofthrones_episodes, R.raw.theflash_episodes};

        for (int i = 0; i < episodeIds.length; i++) {
            Show show = realm.where(Show.class).equalTo("id", i+1).findFirst();
            if (show != null) {
                parseEpisodes(episodeIds[i], realm, show);
            }
        }
    }

    private static void parseEpisodes(int episodeId, Realm realm, Show show) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        String json = fileGetContents(ApplicationController.getInstance(), episodeId);
        Type listType = new TypeToken<List<Episode>>() {
        }.getType();

        realm.beginTransaction();

        Season season = realm.createObject(Season.class, show.getId());
        season.setShow(show);
        List<Episode> episodeList = gson.fromJson(json.toString(), listType);
        for (Episode episode : episodeList) {
            Episode newEpisode = realm.createObject(Episode.class, episode.getId());
            newEpisode.setTitle(episode.getTitle());
            newEpisode.setNumber(episode.getNumber());
            newEpisode.setEmissionDate(episode.getEmissionDate());
            newEpisode.setOverview(episode.getOverview());

            int seasonNumber = episode.getId() / 10;
            season.setNumber(seasonNumber);

            newEpisode.setSeason(season);
        }

        realm.commitTransaction();
    }
}
