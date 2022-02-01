package com.example.sportnetsheled;

import android.app.Activity;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

public class UserClass implements Serializable {
    private String userName;
    private String firstName, lastName;
    private String uid; // - user id - already given by authFirebase
    private String[] muscles; // see - static class MusclesClass
    private boolean isTrainer;

    public final static int REQUEST_CODE = 32145;


    public UserClass() {
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUid() {
        return uid;
    }

    public String[] getMuscles() {
        return muscles;
    }

    public boolean isTrainer() {
        return isTrainer;
    }


}
