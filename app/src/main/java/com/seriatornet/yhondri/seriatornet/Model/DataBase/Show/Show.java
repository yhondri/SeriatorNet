package com.seriatornet.yhondri.seriatornet.Model.DataBase.Show;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by yhondri on 26/03/2018.
 */

public class Show extends RealmObject {

    @PrimaryKey
    private String id;
    @Required
    private String name;
    @Required
    private String description;
    private int runtime;
    private String country;
    private String language;
    private String genre;

    public void setId(String id) {
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

    public String getId() {

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
}
