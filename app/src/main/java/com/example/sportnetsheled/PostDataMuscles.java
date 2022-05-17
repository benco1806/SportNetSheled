package com.example.sportnetsheled;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

public class PostDataMuscles extends AppCompatActivity {
    private NumberPicker nbSets, nbReps;
    private CheckBox[] checkBoxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_data_muscles);

        nbSets = (NumberPicker) findViewById(R.id.nbsets);
        nbSets.setMinValue(1);
        nbSets.setMaxValue(10);
        nbSets.setWrapSelectorWheel(true);

        nbReps = (NumberPicker) findViewById(R.id.nbreps);
        nbReps.setMinValue(1);
        nbReps.setMaxValue(30);
        nbReps.setWrapSelectorWheel(true);

        //se
        checkBoxes = new CheckBox[]{(CheckBox) findViewById(R.id.ch_shoulders), (CheckBox) findViewById(R.id.ch_chest), (CheckBox) findViewById(R.id.ch_sixpack), (CheckBox) findViewById(R.id.ch_biceps),
                (CheckBox) findViewById(R.id.ch_forearms), (CheckBox) findViewById(R.id.ch_quads), (CheckBox) findViewById(R.id.ch_calves), (CheckBox) findViewById(R.id.ch_upperback),
                (CheckBox) findViewById(R.id.ch_triceps), (CheckBox) findViewById(R.id.ch_lowerback), (CheckBox) findViewById(R.id.ch_gluts)};
    }
}