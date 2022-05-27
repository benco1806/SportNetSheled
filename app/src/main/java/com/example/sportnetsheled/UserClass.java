package com.example.sportnetsheled;


import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

@IgnoreExtraProperties
public class UserClass implements Serializable {
    private String userName;
    private String firstName, lastName, email;
    private String uid; // - user id - already given by authFirebase
    private ArrayList<String> muscles; // see - static class MusclesClass
    private ArrayList<String> following;

    public final static int REQUEST_CODE = 32145;


    public UserClass() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserClass(String userName, String firstName, String lastName, String email, String uid, ArrayList<String> muscles) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.uid = uid;
        this.muscles = muscles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public ArrayList<String> getMuscles() {
        return muscles;
    }

    public ArrayList<String> getFollowing() {
        return following;
    }



    public static void lookForUser(String uid, ProgressDialog progressDialog, MainActivity main) {
        FirebaseDatabase.getInstance().getReference("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot pos: snapshot.getChildren()) {
                    UserClass user = pos.getValue(UserClass.class);
                    if(uid.equals(user.getUid())){
                        pos.getRef().child("trainer").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Log.e("USER.CLASS:onDataChange", "Error getting data", task.getException());

                                }
                                else {
                                    String s = String.valueOf(task.getResult().getValue());
                                    Log.d("USER.CLASS:onDataChange", s);
                                    MainActivity.USER = user;
                                    MainActivity.USER_REFERENCE = pos.getRef();
                                    progressDialog.dismiss();
                                    main.onUserDataHasSynchronized();
                                }
                            }
                        });
                        return;
                    }
                }
                progressDialog.dismiss();
                Log.e("USER.CLASS:onDataChange","can not find the user: " + uid);
                Toast.makeText(main, "USER.CLASS:onDataChange error", Toast.LENGTH_SHORT).show();
                System.exit(-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("USER.CLASS:onCancelled",error.toException().getLocalizedMessage());
                Toast.makeText(main, "USER.CLASS:onDataCancelled error", Toast.LENGTH_SHORT).show();
                System.exit(-1);
            }
        });
    }

}
