package com.example.sportnetsheled;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;
import java.util.List;

public class HomeListViewAdapter extends BaseAdapter {


    private Context context;
    private List<Post> posts;
    LayoutInflater layoutInflater;
    PostManager pm;

    public HomeListViewAdapter(Context context, List<Post> p, PostManager pm) {
        this.context = context;
        this.posts = p;
        this.pm = pm;


    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int i) {
        return posts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Post post = posts.get(i);

        if(view==null){
            layoutInflater = LayoutInflater.from(this.context);
            view=layoutInflater.inflate(R.layout.post_layout,null);
        }
        TextView tv = view.findViewById(R.id.tvPost);
        tv.setText(post.getName());
        VideoView vv = view.findViewById(R.id.videoView);


        try {
            pm.getUri(post, vv);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return view;
    }





}
