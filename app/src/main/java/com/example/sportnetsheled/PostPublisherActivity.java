package com.example.sportnetsheled;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class PostPublisherActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etTextApp;
    private ImageButton ib;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_publisher);

        etTextApp = (EditText) findViewById(R.id.etTextApp);
        ib = (ImageButton) findViewById(R.id.uploadVideo);
        button = (Button) findViewById(R.id.btPublish);

        ib.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

    }
}