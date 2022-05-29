package com.example.sportnetsheled.ui;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.LayoutRes;

import com.example.sportnetsheled.R;
import com.example.sportnetsheled.SigningUpActivity;

public class IDFragment extends CustomFragment implements View.OnClickListener{

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

        btNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btNext & firstName.getText() != null & lastName.getText() != null && userName.getText() != null){
            activity.onIDFregmantDone(firstName.getText().toString(), lastName.getText().toString(), userName.getText().toString());
        }
    }
}
