package com.example.sportnetsheled;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PostManager {
    // Create a Cloud Storage reference from the app
    private FirebaseStorage storage;
    private DatabaseReference postsRef;
    private PostsReffInClass postsReffInClass;
    private MainActivity main;


    public PostManager(MainActivity main) {
        this.main = main;
        storage = FirebaseStorage.getInstance("gs://sportnet-e4209.appspot.com/");
        postsRef = FirebaseDatabase.getInstance().getReference().child("postsReffInClass");
        postsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postsReffInClass = snapshot.getValue(PostsReffInClass.class);

                if(postsReffInClass == null){
                    postsReffInClass = new PostsReffInClass();
                    postsReffInClass.posts = new ArrayList<>();
                }else{
                    for (int i = 0; i < postsReffInClass.posts.size(); i ++)
                        postsReffInClass.posts.get(i).setUriHere(false);
                }
                main.onPostsLoaded(getPostsReffInClass().posts);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getUri(Post p, VideoView vv) throws IOException {
        StorageReference reference = storage.getReference(p.getPath());
        File localFile = File.createTempFile(p.getTextApp(), ".mp4");
        p.setUriHere(true);
        Log.d("TaskDownLoad", "starting...");
        reference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Uri uri = Uri.fromFile(localFile);
                vv.setVideoURI(uri);
                vv.start();
                vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        vv.start();
                    }
                });
                Log.d("TaskDownLoad", "we did it!:: " + localFile.getPath());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("TaskDownLoad", "blyat:: " + localFile.getPath());
            }
        });
    }

    public void updatePostsRefOut(){
        postsRef.setValue(postsReffInClass);
    }

    public PostsReffInClass getPostsReffInClass() {
        return postsReffInClass;
    }
}
