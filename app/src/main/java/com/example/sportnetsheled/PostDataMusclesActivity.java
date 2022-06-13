package com.example.sportnetsheled;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.util.ArrayList;

public class PostDataMusclesActivity extends AppCompatActivity implements View.OnClickListener {
    private NumberPicker nbSets, nbReps;
    private CheckBox[] checkBoxes;
    private Button publish;
    private Intent intent;
    private MyReceiver receiver;

    public static final String MUSCLESNAMETAG = "MUSCLESNAMETAG", SETSNAMETAG = "SETSNAMETAG", REPSNAMETAG = "REPSNAMETAG",
    WORKOUTNAMENAMETAG = "WORKOUTNAMENAMETAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_data_muscles);
        receiver = new MyReceiver();
        intent = getIntent();

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

        publish = (Button)findViewById(R.id.button);
        publish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ArrayList<String> tmp = new ArrayList<>();
        for (CheckBox ch:checkBoxes) {
            String name = ch.getText().toString();
            if (ch.isChecked()) {
                switch(name){
                    case "shoulders":
                        tmp.add(MusclesClass.SHOULDERS);
                        break;
                    case "chest":
                        tmp.add(MusclesClass.CHEST);
                        break;
                    case "6 pack":
                        tmp.add(MusclesClass.SIXPACK);
                        break;
                    case "biceps":
                        tmp.add(MusclesClass.BICEPS);
                        break;
                    case "forearms":
                        tmp.add(MusclesClass.FOREARMS);
                        break;
                    case "quads":
                        tmp.add(MusclesClass.QUADS);
                        break;
                    case "calves":
                        tmp.add(MusclesClass.CALVES);
                        break;
                    case "upper back":
                        tmp.add(MusclesClass.UPPERBACK);
                        break;
                    case "triceps":
                        tmp.add(MusclesClass.TRICEPS);
                        break;
                    case "lower back":
                        tmp.add(MusclesClass.LOWERBACK);
                        break;
                    case "gluts":
                        tmp.add(MusclesClass.GLUTS);
                }
            }
        }//end of the for;
        String[] muscles = new String[tmp.size()];
        for (int i = 0; i < muscles.length; i++)
            muscles[i] = tmp.get(i);
        intent.putExtra(MUSCLESNAMETAG, muscles);

        int sets = nbSets.getValue();
        int reps = nbReps.getValue();

        intent.putExtra(SETSNAMETAG, sets);
        intent.putExtra(REPSNAMETAG, reps);

        String workoutname = ((EditText)(findViewById(R.id.etworkoutname))).getText().toString();
        intent.putExtra(WORKOUTNAMENAMETAG, workoutname);
        setResult(RESULT_OK, intent);
        finish();
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