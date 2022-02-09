package com.example.sportnetsheled;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserClass {
    private String userName;
    private String firstName, lastName;
    private String uid; // - user id - already given by authFirebase
    private String[] muscles; // see - static class MusclesClass
    private boolean isTrainer;

    public final static int REQUEST_CODE = 32145;


    public UserClass() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserClass(String userName, String firstName, String lastName, String uid, boolean isTrainer) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.uid = uid;
        this.isTrainer = isTrainer;
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
