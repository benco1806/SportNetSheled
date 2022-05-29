package com.example.sportnetsheled;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.sportnetsheled.ui.EPFragment;
import com.example.sportnetsheled.ui.IDFragment;
import com.example.sportnetsheled.ui.MusclesFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class SigningUpActivity extends AppCompatActivity {

    private String firstName, lastName, userName; //first group
    private String email;
    private ArrayList<String> muscles;
    private IDFragment idFragment;
    private EPFragment epFragment;
    private MusclesFragment musclesFragment;
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


    public void onIDFregmantDone(String firstName, String lastName, String userName)
    {
        Log.e(ID_SIGNUP_DATARETURNED, "firstName: " + firstName +", last name: " + lastName + ", user name: " + userName);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        idFragment.destroy();
        setupMusclesFragmentDone();

    }

    public void onEPFragmentDone(String email, String password){
        this.email = email;
        Log.d(EP_SIGNUP_DATARETURNED, "email: " + email + " ,password: " + password);
        FirebaseAuth mAuth;

        mAuth = FirebaseAuth.getInstance();
        //progress annimation:
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("please wait");
        pd.setCancelable(false);
        pd.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(EP_SIGNUP_DATARETURNED, "createUserWithEmail:success");
                            pd.dismiss();
                            comeBackToMainUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(EP_SIGNUP_DATARETURNED, "createUserWithEmail:failure", task.getException());
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "Authentication failed, try again",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    public void setupMusclesFragmentDone(){
        musclesFragment = new MusclesFragment(R.layout.fregmant_muscles,this);
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(false).replace(R.id.signup_container,musclesFragment
        ).commit();
    }

    public void onMusclesFragmentDone(ArrayList<String> muscles){
        this.muscles = muscles;
        musclesFragment.destroy();
        setUpEPfragment();
    }

    private void comeBackToMainUI() {

        //setting an array for muscles:
        String[] muscles = new String[this.muscles.size()];
        for (int i = 0; i < muscles.length; i++)
            muscles[i] = this.muscles.get(i);

        Intent intent = new Intent();
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastname", lastName);
        intent.putExtra("username", userName);
        intent.putExtra("muscles",muscles);
        intent.putExtra("email", email);
        setResult(RESULT_OK, intent);
        finish();
    }
}