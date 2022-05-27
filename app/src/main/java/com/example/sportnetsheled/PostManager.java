package com.example.sportnetsheled;

import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

    private MainActivity main;


    public PostManager(MainActivity main) {
        this.main = main;
        storage = FirebaseStorage.getInstance("gs://sportnet-e4209.appspot.com/");
        postsRef = FirebaseDatabase.getInstance().getReference().child("posts");
        postsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Post> posts = new ArrayList<>();
                for(DataSnapshot shot: snapshot.getChildren()){
                    Post post = shot.getValue(Post.class);
                    if(post != null)
                        posts.add(post);
                }
                main.onPostsLoaded(posts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getUri(Post p, VideoView vv) throws IOException {

        File localFile = new File(main.getCacheDir(),p.getFilename());


        if (!localFile.exists()) {
            StorageReference reference = storage.getReference(p.getPath());
            File tempFile = new File(main.getCacheDir(),p.getFilename() + ".mp4");

            Log.d("TaskDownLoad", "starting...");
            reference.getFile(tempFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    setUriVideo(tempFile, vv);
                    Log.d("TaskDownLoad", "we did it!:: " + tempFile.getPath());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("TaskDownLoad", "blyat:: " + exception.getLocalizedMessage());
                }
            });
        }else{
            setUriVideo(localFile, vv);
        }
    }





    private void setUriVideo(File file, VideoView vv){
        Uri uri = Uri.fromFile(file);
        vv.setVideoURI(uri);
        vv.seekTo(1);

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
    }

    public static DatabaseReference getPostsRef(){
        return FirebaseDatabase.getInstance().getReference().child("posts");
    }
}
