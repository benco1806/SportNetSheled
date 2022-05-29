package com.example.sportnetsheled.ui;

import android.content.Context;
import android.widget.ListView;


import com.example.sportnetsheled.myAdapter;
import com.example.sportnetsheled.MainActivity;
import com.example.sportnetsheled.Post;
import com.example.sportnetsheled.R;

import java.util.ArrayList;

public class HomeFragment extends CustomFragment {

    private ArrayList<Post> posts;
    private ListView lv;
    private myAdapter adapter;
    private Context context;

    public void refresh() {
        adapter.setPosts(posts);
    }

    public HomeFragment(int layout, Context context) {
        super(layout, context);
        this.context = context;
        posts = new ArrayList<>();

    }


    @Override
    protected void intilaize() {
        lv = (ListView) thisView.findViewById(R.id.list);
        adapter = new myAdapter(context, posts, MainActivity.postManager);
        lv.setAdapter(adapter);
    }


    public void onPostsLoaded(ArrayList<Post> posts) {
        this.posts = posts;

    }

    @Override
    public void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }
}
