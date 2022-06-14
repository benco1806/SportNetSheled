package com.example.sportnetsheled;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class ProfileViewerActivity extends AppCompatActivity {

    private TextView tv;
    private ProfileGridAdapter adapter;
    private ArrayList<Post> posts = new ArrayList<>();
    private ProgressDialog pd;
    private String uid, userName;
    private MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        receiver = new MyReceiver();
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        userName = intent.getStringExtra("username");

        setContentView(R.layout.fragment_profile);
        //showing a dialog
        pd = new ProgressDialog(this);
        pd.setMessage("loading data...");
        pd.setCancelable(false);
        pd.show();

        getPosts();

        tv = (TextView) findViewById(R.id.textView);
        tv.setText("@"+userName);

        adapter = new ProfileGridAdapter(this, posts, null);
        GridView gridView = (GridView)findViewById(R.id.profileGrid);
        gridView.setAdapter(adapter);

        ((TextView)findViewById(R.id.tvmywork)).setText("user's workouts:");
    }

    private void getPosts() {
        PostManager.getPostsRef().get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful() && task.getResult().exists()){
                    ArrayList<Post> posts = new ArrayList<>();
                    for(DataSnapshot shot : task.getResult().getChildren()){
                        Post p = shot.getValue(Post.class);
                        p.setRefName(shot.getKey());
                        if(p.getUiduser().equals(uid))
                            posts.add(p);
                    }
                    ProfileViewerActivity.this.posts = posts;
                    adapter.setPosts(ProfileViewerActivity.this.posts);
                    pd.dismiss();
                }
            }
        });

    }

    protected void onStart() {
        registerReceiver(receiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(receiver);
        super.onStop();
    }

}