package com.example.sportnetsheled.ui;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.LayoutRes;

import com.example.sportnetsheled.R;
import com.example.sportnetsheled.SigningUpActivity;

public class IDFragment extends CustomFragment{

    private SigningUpActivity activity;
    private EditText firstName, lastName, userName;
    private Button btNext;
    private RadioButton rbTrainer;

    public IDFragment(@LayoutRes int layout, Context context) {
        super(layout, context);
        activity = (SigningUpActivity) context;
    }

    @Override
    protected void intilaize() {
        firstName = (EditText) thisView.findViewById(R.id.firstnamesignup);
        lastName = (EditText) thisView.findViewById(R.id.lastnamesignup);
        userName = (EditText) thisView.findViewById(R.id.usernamesignup);
        btNext = thisView.findViewById(R.id.btnextidsignup);
        rbTrainer = thisView.findViewById(R.id.trainersignupradio);


    }
}
