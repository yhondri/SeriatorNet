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
    private String title;
    private String descriptiosn;
    private String imageURL;
    private int number;
    private Date emissionDate;
    private Season season;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriptiosn() {
        return descriptiosn;
    }

    public void setDescriptiosn(String descriptiosn) {
        this.descriptiosn = descriptiosn;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getEmissionDate() {
        return emissionDate;
    }

    public void setEmissionDate(Date emissionDate) {
        this.emissionDate = emissionDate;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }
}
