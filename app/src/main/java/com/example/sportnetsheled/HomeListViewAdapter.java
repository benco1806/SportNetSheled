package com.example.sportnetsheled;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class HomeListViewAdapter extends BaseAdapter {


    private Context context;
    private List<Post> posts;
    LayoutInflater layoutInflater;
    PostManager pm;

    public HomeListViewAdapter(Context context, List<Post> p, PostManager pm) {
        this.context = context;
        this.posts = p;
        this.pm = pm;

        //bug fixing... the Post.isUriHere might be true... so we might false it
        for(int i = 0; i < posts.size(); i++){
            posts.get(i).setUriHere(false);
        }
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
        tv.setText(post.getTextApp());
        VideoView vv = view.findViewById(R.id.videoView);


        try {
            pm.getUri(post, vv);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return view;
    }





}
