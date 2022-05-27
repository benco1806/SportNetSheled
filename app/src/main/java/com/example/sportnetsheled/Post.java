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
    private ArrayList<String> likesuid;
    private String userName;

    public Post(String path, String name, String filename, String uiduser, ArrayList<String> muscles, int sets, int reps, ArrayList<String> likesuid, String userName) {
        this.path = path;
        this.name = name;
        this.filename = filename;
        this.uiduser = uiduser;
        this.muscles = muscles;
        this.sets = sets;
        this.reps = reps;
        this.likesuid = likesuid;
        this.userName = userName;
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


    public ArrayList<String> getLikesuid() {
        return likesuid;
    }

    public String getUserName() {
        return userName;
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
