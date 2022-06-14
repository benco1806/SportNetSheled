package com.example.sportnetsheled.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.sportnetsheled.MainActivity;
import com.example.sportnetsheled.Post;
import com.example.sportnetsheled.PostManager;
import com.example.sportnetsheled.ProfileGridAdapter;
import com.example.sportnetsheled.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class ProfileFragment extends CustomFragment implements AdapterView.OnItemLongClickListener {

    private TextView tv;
    private ProfileGridAdapter adapter;
    private ArrayList<Post> myposts;

    public ProfileFragment(int layout, Context context) {
        super(layout, context);
    }

    @Override
    protected void intilaze() {
        myposts = new ArrayList<>();

        lookforMyposts();

        adapter = new ProfileGridAdapter(context, myposts);
        GridView gridView = (GridView) thisView.findViewById(R.id.profileGrid);
        gridView.setOnItemLongClickListener(this);

        gridView.setAdapter(adapter);

        tv = (TextView)thisView.findViewById(R.id.textView);

        if (!isSynchronized){
            onUserDataHasSynchronized();
            isSynchronized = false;
        }

    }



    @Override
    public void onUserDataHasSynchronized() {
        if(thisView != null){
          tv.setText("@" + MainActivity.USER.getUserName());
        }else{
            isSynchronized = false;
        }
    }


    private void lookforMyposts(){
        PostManager.getPostsRef().get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful() && task.getResult().exists()){
                    ArrayList<Post> posts = new ArrayList<>();
                    for(DataSnapshot shot : task.getResult().getChildren()){
                        Post p = shot.getValue(Post.class);
                        p.setRefName(shot.getKey());
                        if(p.getUiduser().equals(MainActivity.USER.getUid()))
                            posts.add(p);
                    }
                    myposts = posts;
                    adapter.setPosts(myposts);
                }
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        lookforMyposts();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return false;
    }
}
