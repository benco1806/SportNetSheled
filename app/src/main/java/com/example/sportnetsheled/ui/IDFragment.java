package com.example.sportnetsheled.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.example.sportnetsheled.MainActivity;
import com.example.sportnetsheled.R;
import com.example.sportnetsheled.SigningUpActivity;
import com.example.sportnetsheled.UserClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IDFragment extends CustomFragment implements View.OnClickListener{

    private SigningUpActivity activity;
    private EditText firstName, lastName, userName;
    private Button btNext;

    public IDFragment(@LayoutRes int layout, Context context) {
        super(layout, context);
        activity = (SigningUpActivity) context;
    }

    @Override
    protected void intilaze() {
        firstName = (EditText) thisView.findViewById(R.id.firstnamesignup);
        lastName = (EditText) thisView.findViewById(R.id.lastnamesignup);
        userName = (EditText) thisView.findViewById(R.id.usernamesignup);
        btNext = thisView.findViewById(R.id.btnextidsignup);

        btNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(!firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() && !userName.getText().toString().isEmpty()){
            checkIfUserExists();

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

    private void checkIfUserExists() {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("please wait...");
        progressDialog.show();

        FirebaseDatabase.getInstance().getReference("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot pos: snapshot.getChildren()) {
                    String username = pos.child("userName").getValue(String.class);
                    if(IDFragment.this.userName.getText().toString().equals(username)){
                        usernameError();
                        progressDialog.dismiss();
                        return;
                    }
                }
                progressDialog.dismiss();
                //continue
                activity.onIDFregmantDone(firstName.getText().toString(), lastName.getText().toString(), userName.getText().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("idfr:onCancelled",error.toException().getLocalizedMessage());
                Toast.makeText(context, "idfr:onDataCancelled error", Toast.LENGTH_SHORT).show();
                System.exit(-1);
            }
        });
    }

    private void usernameError() {
        new AlertDialog.Builder(context)
                .setTitle("Attention")
                .setMessage("username is already taken")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setNeutralButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
