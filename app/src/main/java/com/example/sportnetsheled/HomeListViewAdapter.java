package com.example.sportnetsheled;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.ArraySet;
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
    Set<View> viewSet;
    PostManager pm;

    public HomeListViewAdapter(Context context, List<Post> p, PostManager pm) {
        this.viewSet = new ArraySet<View>();
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
        ViewHolder viewHolder;

        if(view==null){
            layoutInflater = LayoutInflater.from(this.context);

            view=layoutInflater.inflate(R.layout.post_layout,null);
            viewHolder = new ViewHolder();

            viewHolder.tvPost = (TextView)view.findViewById(R.id.tvPost);
            viewHolder.videoView = (VideoView)view.findViewById(R.id.videoView);

            //info button
            viewHolder.infoButton = (ImageView) view.findViewById(R.id.info_post);


            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }

        final Post post = posts.get(i);

        viewHolder.tvPost.setText(post.getTextApp());

        //showing the info of the post:
        viewHolder.infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        if (!post.isUriHere()) {
            try {
                pm.getUri(post, viewHolder.videoView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            VideoView vv = viewHolder.videoView;
            vv.start();
            vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    vv.start();
                }
            });
        }

        viewSet.add(view);
        return view;
    }

    private static class ViewHolder{
        public TextView tvPost;
        public VideoView videoView;
        public ImageView infoButton;
    }



}
