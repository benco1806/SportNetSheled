package com.example.sportnetsheled;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;

@IgnoreExtraProperties
public class PostsReffInClass implements Serializable {
    public ArrayList<Post> posts;

    public PostsReffInClass() {
    }


}
