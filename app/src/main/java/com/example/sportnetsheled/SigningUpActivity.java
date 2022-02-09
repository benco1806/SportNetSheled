package com.example.sportnetsheled;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.sportnetsheled.ui.EPFragment;
import com.example.sportnetsheled.ui.IDFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigningUpActivity extends AppCompatActivity {

    private String firstName, lastName, userName; //first group
    private boolean isTrainer;
    private IDFragment idFragment;
    private EPFragment epFragment;
    private final String ID_SIGNUP_DATARETURNED = "id.signup.datareturned", EP_SIGNUP_DATARETURNED = "ep.signup.datareturned";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signing_up);

        idFragment = new IDFragment(R.layout.id_signup_layout, this);


        getSupportFragmentManager().beginTransaction().setReorderingAllowed(false).replace(R.id.signup_container,idFragment
                ).commit();
    }

    private void setUpEPfragment(){
        epFragment = new EPFragment(R.layout.email_password_signup_layout,this);
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(false).replace(R.id.signup_container,epFragment
        ).commit();
    }


    public void onIDFregmantDone(String firstName, String lastName, String userName, boolean isTrainer)
    {
        Log.e(ID_SIGNUP_DATARETURNED, "firstName: " + firstName +", last name: " + lastName + ", user name: " + userName + ", isTrainer? " + isTrainer);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        idFragment.onDestroy();
        idFragment = null;
        setUpEPfragment();

    }

    public void onEPFragmentDone(String email, String password){
        Log.d(EP_SIGNUP_DATARETURNED, "email: " + email + " ,password: " + password);
        FirebaseAuth mAuth;

        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(EP_SIGNUP_DATARETURNED, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                            comeBackToMainUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(EP_SIGNUP_DATARETURNED, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed, try again",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });

    }

    private void comeBackToMainUI() {
        Intent intent = new Intent();
        intent.putExtra("firatName", firstName);
        intent.putExtra("lastname", lastName);
        intent.putExtra("username", userName);
        intent.putExtra("isTrainer?", isTrainer);
        setResult(RESULT_OK, intent);
        finish();
    }
}