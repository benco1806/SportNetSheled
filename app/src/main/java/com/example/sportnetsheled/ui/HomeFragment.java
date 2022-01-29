package com.example.sportnetsheled.ui;

import android.content.Context;
import android.widget.ListView;


import com.example.sportnetsheled.HomeListViewAdapter;
import com.example.sportnetsheled.Post;
import com.example.sportnetsheled.R;

import java.util.ArrayList;

public class HomeFragment extends CustomFragment {



    public HomeFragment(int layout, Context context) {
        super(layout, context);
    }


    @Override
    protected void intilaize() {
        ArrayList<Post> posts = new ArrayList<Post>();
        Post p = new Post("1111", "gs://todolistbenfirebase.appspot.com/20211229_094441.mp4");
        Post p1 = new Post("kfv;~~dl", "gs://todolistbenfirebase.appspot.com/20211229_094450.mp4");

        posts.add(p);
        posts.add(p1);


        HomeListViewAdapter adapter = new HomeListViewAdapter(getContext(), posts);

        ListView lv = (ListView) thisView.findViewById(R.id.list);

        lv.setAdapter(adapter);
    }
}
