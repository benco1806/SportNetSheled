package com.example.sportnetsheled;


import android.app.ProgressDialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

@IgnoreExtraProperties
public class UserClass {
    private String userName;
    private String firstName, lastName;
    private String uid; // - user id - already given by authFirebase
    private String[] muscles; // see - static class MusclesClass
    private boolean isTrainer;

    public final static int REQUEST_CODE = 32145;


    public UserClass() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserClass(String userName, String firstName, String lastName, String uid, boolean isTrainer) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.uid = uid;
        this.isTrainer = isTrainer;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUid() {
        return uid;
    }

    public String[] getMuscles() {
        return muscles;
    }

    public boolean isTrainer() {
        return isTrainer;
    }


    public static void lookForUser(String uid, Context context) {
        FirebaseDatabase.getInstance().getReference("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProgressDialog pd = new ProgressDialog(context);
                pd.setMessage("loading");
                pd.setCancelable(false);
                pd.show();

                for (DataSnapshot pos: snapshot.getChildren()) {
                    UserClass user = pos.getValue(UserClass.class);
                    if(uid.equals(user.getUid())){
                        MainActivity.user = user;
                        pd.dismiss();
                        return;
                    }
                }
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
