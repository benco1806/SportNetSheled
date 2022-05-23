package com.example.sportnetsheled.ui;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.sportnetsheled.MusclesClass;
import com.example.sportnetsheled.R;
import com.example.sportnetsheled.SigningUpActivity;

import java.util.ArrayList;

public class MusclesFragment extends CustomFragment implements View.OnClickListener{



    private SigningUpActivity activity;
    private CheckBox[] checkBoxes;
    private Button next;

    public MusclesFragment(int layout, Context context) {
        super(layout, context);
        activity = (SigningUpActivity) context;
    }

    @Override
    protected void intilaize() {
        checkBoxes = new CheckBox[]{(CheckBox) thisView.findViewById(R.id.ch_shoulders), (CheckBox) thisView.findViewById(R.id.ch_chest), (CheckBox) thisView.findViewById(R.id.ch_sixpack), (CheckBox) thisView.findViewById(R.id.ch_biceps),
                (CheckBox) thisView.findViewById(R.id.ch_forearms), (CheckBox)thisView.findViewById(R.id.ch_quads), (CheckBox) thisView.findViewById(R.id.ch_calves), (CheckBox) thisView.findViewById(R.id.ch_upperback),
                (CheckBox) thisView.findViewById(R.id.ch_triceps), (CheckBox) thisView.findViewById(R.id.ch_lowerback), (CheckBox) thisView.findViewById(R.id.ch_gluts)};

        next = (Button)thisView.findViewById(R.id.button);
        next.setOnClickListener(this);
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
        }
        activity.onMusclesFragmentDone(tmp);
    }
}
