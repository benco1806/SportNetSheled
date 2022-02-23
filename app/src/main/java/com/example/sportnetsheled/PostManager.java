package com.example.sportnetsheled;

import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.VideoView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class PostManager {
    // Create a Cloud Storage reference from the app
    FirebaseStorage storage = FirebaseStorage.getInstance();

    public void getUri(Post p, VideoView vv) throws IOException {
        StorageReference reference = storage.getReferenceFromUrl(p.getPath());
        File localFile = File.createTempFile("i"+ "gzo", ".mp4");
        p.setUriHere(true);
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
}
