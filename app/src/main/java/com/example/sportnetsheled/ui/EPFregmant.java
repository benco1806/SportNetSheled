package com.example.sportnetsheled.ui;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sportnetsheled.R;
import com.example.sportnetsheled.SigningUpActivity;

public class EPFregmant extends CustomFragment implements View.OnClickListener{


    private EditText email, password;
    private SigningUpActivity activity;
    private Button btnFinish;

    public EPFregmant(int layout, Context context) {
        super(layout, context);
        activity = (SigningUpActivity) context;
    }

    @Override
    protected void intilaize() {
        email = (EditText) thisView.findViewById(R.id.emailsignupet);
        password = (EditText) thisView.findViewById(R.id.passwordsignupet);
        btnFinish = thisView.findViewById(R.id.finishbtsignup);
        btnFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
