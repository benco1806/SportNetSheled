package com.example.sportnetsheled;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.sportnetsheled.ui.CustomFragment;
import com.example.sportnetsheled.ui.IDFragment;

public class SigningUpActivity extends AppCompatActivity {

    private String firstName, lastName, userName; //first group
    private boolean isTrainer;
    private IDFragment idFragment;
    //private
    private final String ID_SIGNUP_DATARETURNED = "id.signup.datareturned";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signing_up);

        idFragment = new IDFragment(R.layout.id_signup_layout, this);

        getSupportFragmentManager().beginTransaction().setReorderingAllowed(false).replace(R.id.signup_container,idFragment
                ).commit();
    }

    private void setUpEPfragment(){

    }


    public void onIDFregmantDone(String firstName, String lastName, String userName, boolean isTrainer)
    {
        Log.e(ID_SIGNUP_DATARETURNED, "firstName: " + firstName +", last name: " + lastName + ", user name: " + userName + ", isTrainer? " + isTrainer);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        idFragment.onDestroy();
        idFragment = null;

    }

    public void onEPFragmentDone(String Email, String Password){

    }
}