package com.seriatornet.yhondri.seriatornet.Model.DataBase;

/**
 * Created by yhondri on 30/3/18.
 */

public class User {

    private String userName;
    private String email;

    public User(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }
}
