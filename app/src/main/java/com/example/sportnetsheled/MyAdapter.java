package com.example.sportnetsheled;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class MyAdapter extends BaseAdapter {


    private Context context;
    private List<Post> posts;
    LayoutInflater layoutInflater;
    PostManager pm;
    Animation animation;

    public MyAdapter(Context context, List<Post> p, PostManager pm) {
        this.context = context;
        this.posts = p;
        this.pm = pm;
        animation = AnimationUtils.loadAnimation(context, R.anim.animation);

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
        ImageButton btLike = (ImageButton) view.findViewById(R.id.btlike);
        TextView tvLikes = (TextView) view.findViewById(R.id.tvLikes);
        TextView tvFollow = (TextView) view.findViewById(R.id.tvFollow);

        if(MainActivity.USER.getUid().equals(post.getUiduser())){
            tvFollow.setVisibility(View.GONE);
        }else{
            if(MainActivity.USER.getFollowing() == null){
                MainActivity.USER.setFollowing();
                tvFollow.setText("follow");
                tvFollow.setTextColor(Color.BLUE);
            }else{



                if(MainActivity.USER.getFollowing().contains(post.getUiduser())){
                    tvFollow.setText("following");
                    tvFollow.setTextColor(Color.GRAY);
                }else{
                    tvFollow.setText("follow");
                    tvFollow.setTextColor(Color.BLUE);
                }
            }
        }

        tvFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvFollow.getText().toString().equals("follow")){
                    setStatusWithUser("follow", post); // to follow
                    tvFollow.setText("following");
                    tvFollow.setTextColor(Color.GRAY);
                }else{
                    setStatusWithUser("following", post); // to unfollow
                    tvFollow.setText("follow");
                    tvFollow.setTextColor(Color.BLUE);
                }
            }
        });


        setLikes(tvLikes, post);

        if(post.getLikesuid() != null && post.getLikesuid().contains(MainActivity.USER.getUid())) {
            btLike.setBackground(ContextCompat.getDrawable(context, R.drawable.favorite));
        }else{
            if(post.getLikesuid() == null)
                post.setAnlikeList();
            btLike.setBackground(ContextCompat.getDrawable(context, R.drawable.favoriteborder));

        }


        btLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(post.getLikesuid() != null && !post.getLikesuid().isEmpty() && post.getLikesuid().contains(MainActivity.USER.getUid())) {
                    post.getLikesuid().remove(MainActivity.USER.getUid());
                    onTaskLikeClick(post, R.drawable.favoriteborder, btLike, tvLikes);

                }else{
                    if(post.getLikesuid() == null)
                        post.setAnlikeList();
                    post.getLikesuid().add(MainActivity.USER.getUid());
                    onTaskLikeClick(post, R.drawable.favorite, btLike, tvLikes);
                }
            }
        });


        tv.setText("Workout name: " + post.getName() + " | sets: " + post.getSets() + " | reps: " + post.getReps());
        tvUser.setText("@" + post.getUserName());

        try {
            pm.getUri(post, vv);
        } catch (IOException e) {
            e.printStackTrace();
        }



        return view;
    }

    private void setStatusWithUser(String s, Post post) {
        if(s.equals("follow")){//to follow
            MainActivity.USER.getFollowing().add(post.getUiduser());
        }else{
            //to unfollow
            MainActivity.USER.getFollowing().remove(post.getUiduser());
        }

        MainActivity.USER_REFERENCE.setValue(MainActivity.USER);
    }

    private void setLikes(TextView tvLikes, Post post) {
        if(post.getLikesuid() == null)
            tvLikes.setText("likes: 0");
        else
            tvLikes.setText("likes: " + post.getLikesuid().size());

    }

    private void onTaskLikeClick(Post post, @DrawableRes int drawable, ImageButton btLike, TextView tvLikes){
        Task<Void> t =  PostManager.getPostsRef().child(post.getRefName()).setValue(post);
        t.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful())
                    Toast.makeText(context.getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                else{
                    btLike.setBackground(ContextCompat.getDrawable(context, drawable));
                    setLikes(tvLikes, post);
                    if(drawable == R.drawable.favorite)
                        btLike.startAnimation(animation);
                }
            }
        });
    }


}
