package com.example.sportnetsheled;


import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;

@IgnoreExtraProperties
public class Post implements Serializable {
    private String path;
    private String name;
    private String filename; //refactor to filename
    private String uiduser;
    private ArrayList<String> muscles;
    private int sets;
    private int reps;





    public Post(String textApp, String path, String uid, String uname, ArrayList<String> muscles, int sets, int reps) {
        this.name = textApp;
        this.path = path;
        this.filename = uname;
        this.uiduser = uid;
        this.muscles = muscles;
        this.sets = sets;
        this.reps = reps;

    }

    public String getFilename() {
        return filename;
    }

    public Post() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUiduser() {
        return uiduser;
    }

    public void setUiduser(String uiduser) {
        this.uiduser = uiduser;
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
