package com.fire.fire.postandcommenttutorial.models;

import java.io.Serializable;

/**
 * Model for user data to be parsed by JSON and sent to a database in Google Firebase
 */

public class User implements Serializable {
    private String user;
    private String email;
    private String photoUrl;
    private String uid;
    private Boolean active;

    public User() {
    }

    public User(String user, String email, String photoUrl, String uid, Boolean active) {

        this.user = user;
        this.email = email;
        this.photoUrl = photoUrl;
        this.uid = uid;
        this.active = active;
    }


    public String getUser() {

        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }


}
