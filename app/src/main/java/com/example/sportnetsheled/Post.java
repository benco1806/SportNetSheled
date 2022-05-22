package com.example.sportnetsheled;


import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;

@IgnoreExtraProperties
public class Post implements Serializable {
    private String path;
    private String textApp;
    private boolean isUriHere;
    private String unmame; //refactor to filename
    private String uid;
    private ArrayList<String> muscles;
    private int sets;
    private int reps;



    public Post(String textApp, String path, String uid, String uname, ArrayList<String> muscles, int sets, int reps) {
        this.textApp = textApp;
        this.path = path;
        isUriHere = false;
        this.unmame = uname;
        this.uid = uid;
        this.muscles = muscles;
        this.sets = sets;
        this.reps = reps;

    }

    public String getUnmame() {
        return unmame;
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

    public void setUnmame(String unmame) {
        this.unmame = unmame;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ArrayList<String> getMuscles() {
        return muscles;
    }

    public void setMuscles(ArrayList<String> muscles) {
        this.muscles = muscles;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}
