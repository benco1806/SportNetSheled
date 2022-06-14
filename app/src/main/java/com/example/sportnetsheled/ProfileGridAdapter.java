package com.example.sportnetsheled;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.example.sportnetsheled.ui.ProfileFragment;

import java.io.IOException;
import java.util.ArrayList;

public class ProfileGridAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<Post> posts;
    private ProfileFragment pf;

    public ProfileGridAdapter(Context context, ArrayList<Post> posts, ProfileFragment pf) {
        this.context = context;
        this.posts = posts;
        this.pf = pf;
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

        ImageButton del = (ImageButton) view.findViewById(R.id.btDel);

        if(pf != null){
            del.setVisibility(View.VISIBLE);
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pf.deletePost(posts.get(i));
                }
            });
        }else{
            del.setVisibility(View.GONE);
        }


        VideoView vv = view.findViewById(R.id.videoView);
        ProgressBar pb = (ProgressBar) view.findViewById(R.id.progressBar);


        try {
            MainActivity.postManager.getUri(posts.get(i), vv, pb);
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
