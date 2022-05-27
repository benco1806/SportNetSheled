package com.example.sportnetsheled;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Log;
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
import java.util.Arrays;

public class PostPublisherActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageButton ib;
    private Button button;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_publisher);


        ib = (ImageButton) findViewById(R.id.uploadVideo);
        button = (Button) findViewById(R.id.btnextpublish);

        ib.setOnClickListener(this);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == ib){
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            startActivityForResult(intent, 0);
        }else if(view == button && uri != null){
            //uploadPost(uri, etTextApp.getText().toString());
            startActivityForResult(new Intent(this, PostDataMuscles.class), 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0){ //taking video
            if (resultCode == RESULT_OK){
                uri = data.getData();
            }else{
                Toast.makeText(getApplicationContext(), "no!", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                //taking out the name:
                String workout_name = data.getStringExtra(PostDataMuscles.WORKOUTNAMENAMETAG);
                //taking out the sets value:
                int sets = data.getIntExtra(PostDataMuscles.SETSNAMETAG, 1);
                //taking out the reps value:
                int reps = data.getIntExtra(PostDataMuscles.REPSNAMETAG, 1);
                //taking out the muscles data... :
                String[] muscles = data.getStringArrayExtra(PostDataMuscles.MUSCLESNAMETAG);

                uploadPost(uri, workout_name, sets, reps, muscles);
            }else{
                Toast.makeText(getApplicationContext(), "no!", Toast.LENGTH_SHORT).show();
            }
        }

    }


    private void uploadPost(Uri uri, String name, int sets, int reps, String[] muscles){

        String filename = getFileName();

       Intent intent = new Intent(this, UploadService.class);
       intent.putExtra("uri", uri.toString());
       intent.putExtra("name", name);
       intent.putExtra("sets", sets);
       intent.putExtra("reps", reps);
       intent.putExtra("muscles", muscles);
       intent.putExtra("filename", filename);

       startService(intent);

        Log.d("uploadtask:", "starting:");
        finish();
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