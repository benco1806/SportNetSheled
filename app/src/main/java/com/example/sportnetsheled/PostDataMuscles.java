package com.example.sportnetsheled;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.NumberPicker;

public class PostDataMuscles extends AppCompatActivity {
    private NumberPicker nbSets, nbReps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_data_muscles);

        nbSets = (NumberPicker) findViewById(R.id.nbsets);
        nbSets.setMinValue(1);
        nbSets.setMaxValue(10);
        nbSets.setWrapSelectorWheel(true);
    }
}