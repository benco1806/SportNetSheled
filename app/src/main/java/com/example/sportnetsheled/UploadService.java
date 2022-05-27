package com.example.sportnetsheled;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;

public class UploadService extends Service {
    public UploadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        Uri uri = Uri.parse(intent.getStringExtra("uri"));
        String name = intent.getStringExtra("name");
        int sets = intent.getIntExtra("sets", 0);
        int reps = intent.getIntExtra("reps", 0);
        String[] tmpMuscles = intent.getStringArrayExtra("muscles");
        String filename = intent.getStringExtra("filename");

        StorageReference videoRef = storageRef.child("video/" + filename + ".mp4");

        ArrayList<String> muscles = new ArrayList<>();
        muscles.addAll(Arrays.asList(tmpMuscles));

        videoRef.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "נהדר!!!!", Toast.LENGTH_SHORT).show();
                    Post post = new Post(videoRef.getPath(), name, filename, MainActivity.USER.getUid(), muscles, sets, reps, null, MainActivity.USER.getUserName());
                    addPostToUser(post);
                    MainActivity.updateUser(MainActivity.USER);
                    Log.d("uploadtask:", "done:");
                    UploadService.this.onDestroy();
                }else{
                    System.exit(-1);
                    Log.d("uploadtask:", "blya");
                }
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    private void addPostToUser(Post post) {
        DatabaseReference postsRef = PostManager.getPostsRef();
        postsRef.push().setValue(post);
    }
}