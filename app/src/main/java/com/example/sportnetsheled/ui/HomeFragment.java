package com.example.sportnetsheled.ui;

import android.content.Context;
import android.widget.ListView;


import com.example.sportnetsheled.HomeListViewAdapter;
import com.example.sportnetsheled.MainActivity;
import com.example.sportnetsheled.Post;
import com.example.sportnetsheled.R;

import java.util.ArrayList;

public class HomeFragment extends CustomFragment {

    private ArrayList<Post> posts;
    private ListView lv;
    private HomeListViewAdapter adapter;
    private Context context;


    public HomeFragment(int layout, Context context) {
        super(layout, context);
        this.context = context;
        posts = new ArrayList<>();
    }


    @Override
    protected void intilaize() {
        lv = (ListView) thisView.findViewById(R.id.list);
        adapter = new HomeListViewAdapter(context, posts, MainActivity.postManager);
        lv.setAdapter(adapter);
    }


    public void onPostsLoaded(ArrayList<Post> posts) {
        this.posts = posts;
        adapter = new HomeListViewAdapter(context, posts, MainActivity.postManager);
        lv.setAdapter(adapter);
    }
}
