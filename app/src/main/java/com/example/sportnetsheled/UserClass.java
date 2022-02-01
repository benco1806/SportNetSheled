package com.example.sportnetsheled;

import java.io.Serializable;

public class UserClass implements Serializable {
    private String userName;
    private String firstName, lastName;
    private String uid; // - user id - already given by authFirebase
    private String[] muscles; // see - static class MusclesClass
    private boolean isTrainer;
}
