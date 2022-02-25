package com.example.sportnetsheled;


import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Post implements Serializable {
    private String path;
    private String textApp;
    private boolean isUriHere;



    public Post(String textApp, String path, String uid) {
        this.textApp = textApp;
        this.path = path;
        isUriHere = false;
    }

    public Post() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTextApp() {
        return textApp;
    }

    public void setTextApp(String textApp) {
        this.textApp = textApp;
    }

    public boolean isUriHere() {
        return isUriHere;
    }

    public void setUriHere(boolean uriHere) {
        this.isUriHere = uriHere;
    }
}
