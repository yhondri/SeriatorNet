package com.seriatornet.yhondri.seriatornet.Model.DataBase.Episode;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.Season.Season;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by yhondri on 28/03/2018.
 */

public class Episode extends RealmObject {

    @PrimaryKey
    private int id;
    private int firebaseId;
    private String title;
    private int number;
    private double rating;
    private String overview;
    private Season season;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(int firebaseId) {
        this.firebaseId = firebaseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }
}
