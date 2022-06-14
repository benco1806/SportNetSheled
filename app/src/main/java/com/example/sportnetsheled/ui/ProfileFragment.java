package com.example.sportnetsheled.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class ProfileFragment extends CustomFragment{

    private TextView tv;
    private ProfileGridAdapter adapter;
    private ArrayList<Post> myposts;

    public ProfileFragment(int layout, Context context) {
        super(layout, context);
    }

    @Override
    protected void intilaze() {


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
        myposts = new ArrayList<>();
        adapter = new ProfileGridAdapter(context, myposts, this);
        GridView gridView = (GridView) thisView.findViewById(R.id.profileGrid);
        gridView.setAdapter(adapter);
        lookforMyposts();

    }



    public void deletePost(Post post) {

        new AlertDialog.Builder(context)
                .setTitle("Attention")
                .setMessage("Are you sure you want do delete this post?")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.no, null)
                .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String ref = post.getRefName(), path = post.getPath();

                        //deleting post from database:
                        PostManager.getPostsRef().child(ref).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                lookforMyposts();
                            }
                        });

                        //deleting video from storage:
                        FirebaseStorage.getInstance("gs://sportnet-e4209.appspot.com/").getReference(path).delete();
                    }
                })
                .show();




    }


}
