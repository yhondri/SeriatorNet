package com.seriatornet.yhondri.seriatornet.Model.DataBase.Show;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.Season.Season;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by yhondri on 26/03/2018.
 */

public class Show extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String name;
    private String country;
    private int runtime;
    @Required
    private String language;
    private String genre;
    private String poster;
    private String banner;
    private String description;
    private RealmList<Season> seasons;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRuntime() {
        return runtime;
    }

    public int getId() {

        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getLanguage() {
        return language;
    }

    public String getGenre() {
        return genre;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public RealmList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(RealmList<Season> seasons) {
        this.seasons = seasons;
    }
}
