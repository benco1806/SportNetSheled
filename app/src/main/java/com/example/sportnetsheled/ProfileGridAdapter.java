package com.example.sportnetsheled;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.VideoView;

import java.io.IOException;
import java.util.ArrayList;

public class ProfileGridAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<Post> posts;

    public ProfileGridAdapter(Context context, ArrayList<Post> posts) {
        this.context = context;
        this.posts = posts;
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

        if(view == null){
            layoutInflater = LayoutInflater.from(this.context);
            view = layoutInflater.inflate(R.layout.profilepage_post_layout, null);
        }
        VideoView vv = view.findViewById(R.id.videoView);

        try {
            MainActivity.postManager.getUri(posts.get(i), vv);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return view;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }
}
