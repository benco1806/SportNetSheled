package com.example.sportnetsheled.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sportnetsheled.R;
import com.example.sportnetsheled.SigningUpActivity;

public class EPFragment extends CustomFragment implements View.OnClickListener{


    private EditText email, password;
    private SigningUpActivity activity;
    private Button btnFinish;

    public EPFragment(int layout, Context context) {
        super(layout, context);
        activity = (SigningUpActivity) context;
    }

    @Override
    protected void intilaze() {
        email = (EditText) thisView.findViewById(R.id.emailsignupet);
        password = (EditText) thisView.findViewById(R.id.passwordsignupet);
        btnFinish = thisView.findViewById(R.id.finishbtsignup);
        btnFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
            activity.onEPFragmentDone(email.getText().toString(), password.getText().toString());
        }else{
            new AlertDialog.Builder(context)
                    .setTitle("Attention")
                    .setMessage("please fill all the text arrays!")
                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setNeutralButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}
