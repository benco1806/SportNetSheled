package com.example.sportnetsheled;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.MediaController;
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

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
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
        TextView tv = view.findViewById(R.id.tvPost), tvUser = view.findViewById(R.id.tvuser);
        VideoView vv = view.findViewById(R.id.videoView);

        tv.setText(post.getName());
        tvUser.setText("@" + post.getUserName());

        try {
            pm.getUri(post, vv);
        } catch (IOException e) {
            e.printStackTrace();
        }

        seekingVid(vv);

        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.seekTo(0);
                vv.start();
            }
        });
        vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vv.isPlaying()){
                    vv.pause();
                }else{
                    vv.start();
                }
            }
        });

        return view;
    }

    private void seekingVid(VideoView vv){
        vv.start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        vv.seekTo(0);
        vv.pause();

    }
}
