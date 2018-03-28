package com.seriatornet.yhondri.seriatornet.Model.DataBase;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.Episode.Episode;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Season.Season;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;

/**
 * Created by yhondri on 28/03/2018.
 */

public class MockDataManager {

    public static void mockShows() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Show show1 = realm.createObject(Show.class, "The_Simpsons");
        show1.setName("The Simpsons");
        show1.setDescription("Set in Springfield, the average American town, the show focuses on " +
                "the antics and everyday adventures of the Simpson family; Homer, Marge, Bart, Lisa" +
                " and Maggie, as well as a virtual cast of thousands. Since the beginning, " +
                "the series has been a pop culture icon, attracting hundreds of celebrities to guest" +
                " star. The show has also made name for itself in its fearless satirical take on " +
                "politics, media and American life in general.");

        show1.setCountry("USA");
        show1.setRuntime(22);
        show1.setLanguage("English");
        show1.setGenre("Animation, Comedy");

        realm.commitTransaction();
    }

    public static void mockSeasons(Show show) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Season season1 = realm.createObject(Season.class, 1);
        season1.setName("Season 1");
        season1.setOrder(1);
        season1.setShow(show);

        realm.commitTransaction();
    }

    public static void mockEpisodes(Season season) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Episode episode = realm.createObject(Episode.class, 1);
        episode.setTitle("The Serfsons");
        episode.setDescriptiosn("In a magical medieval world, Marge's mother is turned into an Ice" +
                " Walker and the only way for Homer to afford the cure is to force Lisa to use " +
                "illegal magic. When the King discovers this, he kidnaps Lisa, and Homer must lead" +
                " a feudal uprising to save her.");
        episode.setImageURL("episode_background_1");
        episode.setNumber(1);
        String dtStart = "2017-10-02T02:00:00Z";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = null;
        try {
            date = format.parse(dtStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        episode.setEmissionDate(date);
        episode.setSeason(season);

        realm.commitTransaction();
    }

}
