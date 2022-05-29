package com.example.sportnetsheled;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class WelcomingActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mUSerDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcoming);

        Button signIn = (Button) findViewById(R.id.signinbutton), signUp = (Button) findViewById(R.id.signupbuttonb);
        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);


        //getting accsses to the reference of the users in the firebase
        mUSerDatabase = FirebaseDatabase.getInstance().getReference().child("users");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signinbutton:
                //sign in action
                signIn();
                break;
            case R.id.signupbuttonb:
                //sign up action
                signUp();
                break;

        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 0) { //google request code
            // The Task returned from this call is always completed, no need to attasssch
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

        }
        if(requestCode == 1 && resultCode == RESULT_OK){  //signing up actions
            String firstName = data.getStringExtra("firstName"),
                    lastName = data.getStringExtra("lastname"),
                    userName = data.getStringExtra("username"),
                    email = data.getStringExtra("email");
            boolean isTrainer = data.getBooleanExtra("isTrainer?", false);

            String[] muscles = data.getStringArrayExtra("muscles");

            Log.i("useridben", "firstname: " + firstName + " lastname: " + lastName + " username: " + userName + " trainer?: " + isTrainer);

            createUser(firstName, lastName, userName, muscles, email);
            getBackIntoMainActivity();
        }
    }


    // sign in actions ... //
    private void signIn() {
        setContentView(R.layout.email_password_signup_layout);
        EditText etEmail = (EditText) findViewById(R.id.emailsignupet),
                etPassword = (EditText) findViewById(R.id.passwordsignupet);

        Button button = (Button) findViewById(R.id.finishbtsignup);
        button.setText("Sign in");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etEmail.getText() != null && etPassword.getText() != null){
                    signingIn(etEmail.getText().toString(), etPassword.getText().toString());
                }
            }
        });
    }

    private void signingIn(String email, String password)
    {
        //progress annimation:
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("please wait");
        pd.setCancelable(false);
        pd.show();
        //
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("GoogleSigningIn", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            pd.dismiss();
                            getBackIntoMainActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("GoogleSigningIn", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed, try again please...",
                                    Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }
                });
    }
    //end sign in actions....//


    //sign up actions...//
    private void signUp(){
        Intent intent = new Intent(this, SigningUpActivity.class);
        startActivityForResult(intent, 1);
    }












    private void createUser(String firstName, String lastName, String userName, String[] arr, String email){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(uid == null){
            Log.e("error_firebase", "there is no firebase user");
            System.exit(-1);
        }

        //converting the array-muscles to arraylist
        ArrayList<String> muscles = new ArrayList<>();
        Collections.addAll(muscles, arr);
        //creating the userClass
        //(String userName, String firstName, String lastName, String email, String uid, ArrayList<String> muscles, String trainer, ArrayList<String> postsFavoriteUid)
        UserClass user = new UserClass(userName, firstName, lastName, email, uid, muscles);

        mUSerDatabase.push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    Log.e("error_firebase", task.getException().toString());
                    System.exit(-1);
                }
            }
        });

    }

    private void getBackIntoMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }



}