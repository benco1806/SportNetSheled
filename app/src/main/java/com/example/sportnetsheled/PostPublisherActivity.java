package com.example.sportnetsheled;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PostPublisherActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etTextApp;
    private ImageButton ib;
    private Button button;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_publisher);

        etTextApp = (EditText) findViewById(R.id.etTextApp);
        ib = (ImageButton) findViewById(R.id.uploadVideo);
        button = (Button) findViewById(R.id.btPublish);

        ib.setOnClickListener(this);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == ib){
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            startActivityForResult(intent, 0);
        }else if(view == button && uri != null){
            uploadPost(uri, etTextApp.getText().toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            uri = data.getData();
        }else{
            Toast.makeText(getApplicationContext(), "no!", Toast.LENGTH_SHORT).show();
        }

    }


    private void uploadPost(Uri uri, String name){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        String filename = getFileName();
        StorageReference videoRef = storageRef.child("video/" + filename + ".mp4");
        videoRef.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "נהדר!!!!", Toast.LENGTH_SHORT).show();
                    Post post = new Post(name, videoRef.getPath(), MainActivity.USER.getUid(), filename);
                    addPostToUser(post);
                    MainActivity.updateUser(MainActivity.USER);
                }else{
                    System.exit(-1);
                }
            }
        });
        finish();
    }

    private void addPostToUser(Post post) {
        ArrayList<Post> list = MainActivity.postManager.getPostsReffInClass().posts;
        list.add(post);
        MainActivity.postManager.updatePostsRefOut();
    }

    @NonNull
    private String getFileName() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        LocalDate localDate = LocalDate.now();
        String date = dtf.format(localDate);

        ZonedDateTime now = ZonedDateTime.now();
        String zone = now.getOffset().toString();
        zone = zone.replace(':','+');

        DateTimeFormatter dlf = DateTimeFormatter.ofPattern("HH-mm-ss");
        LocalTime localTime = LocalTime.now();
        String time = dlf.format(localTime);

        return MainActivity.USER.getUid() + "$" + date + "_" + time + "$" + zone + "$" + MainActivity.USER.getUserName() + "$";
    }
}