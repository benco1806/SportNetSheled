package com.example.sportnetsheled;


import android.net.Uri;

public class Post {
    private String path;
    private String textApp;
    private Uri uri;

    public Post(String textApp, String path) {
        this.textApp = textApp;
        this.path = path;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
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
