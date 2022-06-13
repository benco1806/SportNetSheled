package com.example.sportnetsheled;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.VideoView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class PostPublisherActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageButton ib;
    private Button button;
    private Uri uri;
    private VideoView videoView;
    private MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_publisher);
        receiver = new MyReceiver();

        ib = (ImageButton) findViewById(R.id.uploadVideo);
        button = (Button) findViewById(R.id.btnextpublish);
        videoView = (VideoView) findViewById(R.id.videoView);


        ib.setOnClickListener(this);
        button.setOnClickListener(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        if(uri != null){
            videoView.setVideoURI(uri);
            videoView.seekTo(1);
            videoView.setVisibility(View.VISIBLE);
            ib.setVisibility(View.GONE);
        }else{
            videoView.setVisibility(View.GONE);
            ib.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if(view == ib){
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            startActivityForResult(intent, 0);
        }else if(view == button && uri != null){
            //uploadPost(uri, etTextApp.getText().toString());
            startActivityForResult(new Intent(this, PostDataMusclesActivity.class), 1);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
                String workout_name = data.getStringExtra(PostDataMusclesActivity.WORKOUTNAMENAMETAG);
                //taking out the sets value:
                int sets = data.getIntExtra(PostDataMusclesActivity.SETSNAMETAG, 1);
                //taking out the reps value:
                int reps = data.getIntExtra(PostDataMusclesActivity.REPSNAMETAG, 1);
                //taking out the muscles data... :
                String[] muscles = data.getStringArrayExtra(PostDataMusclesActivity.MUSCLESNAMETAG);

                uploadPost(uri, workout_name, sets, reps, muscles);
            }else{
                Toast.makeText(getApplicationContext(), "no!", Toast.LENGTH_SHORT).show();
            }
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
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



    @RequiresApi(api = Build.VERSION_CODES.O)
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