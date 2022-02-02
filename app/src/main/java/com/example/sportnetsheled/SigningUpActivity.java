package com.example.sportnetsheled;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SigningUpActivity extends AppCompatActivity {

    private String firstName, lastName, userName; //first group
    private boolean isTrainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signing_up);

        getSupportFragmentManager().beginTransaction().setReorderingAllowed(false).replace(R.id.signup_container,null
                ).commit();
    }


    public void onIDFregmantDone(String firstName, String lastName, String userName, boolean isTrainer)
    {

    }

    public void onEPFragmentDone(String Email, String Password){

    }
}