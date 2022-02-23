package com.example.sportnetsheled;


import android.net.Uri;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Post {
    private String path;
    private String textApp;
    private Boolean isUriHere; // ##we must finding a solution - saving it in firebase

    public Post(String textApp, String path) {
        this.textApp = textApp;
        this.path = path;
        isUriHere = false;
    }

    public Post() {
    }

    public Boolean isUriHere() {
        return isUriHere;
    }

    public void setUriHere(Boolean uriHere) {
        isUriHere = uriHere;
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
}
