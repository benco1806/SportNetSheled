package com.example.sportnetsheled;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomingActivity extends AppCompatActivity implements View.OnClickListener {

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcoming);

        Button signIn = (Button) findViewById(R.id.signinbutton), signUp = (Button) findViewById(R.id.signupbuttonb);
        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);

        SignInButton signInButton = findViewById(R.id.googlesigminbtn);
        signInButton.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signinbutton:
                //sign in action
                break;
            case R.id.signupbuttonb:
                //sign up action
                signUp();
                break;
            case R.id.googlesigminbtn:
                //google sign in action
                googleSignIn();
                break;
        }
    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 0) { //google request code
            // The Task returned from this call is always completed, no need to attasssch
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        if(requestCode == 1 && resultCode == RESULT_OK){
            String firstName = data.getStringExtra("firstName"),
                    lastName = data.getStringExtra("lastName"),
                    userName = data.getStringExtra("username");
            boolean isTrainer = data.getBooleanExtra("isTrainer?", false);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(getApplicationContext(), "welcome", Toast.LENGTH_SHORT).show();
            // Signed in successfully, show authenticated UI.
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(getApplicationContext(), "can not sign in", Toast.LENGTH_SHORT).show();
            //updateUI(null);
        }
    }

    private void signUp(){
        Intent intent = new Intent(this, SigningUpActivity.class);
        startActivityForResult(intent, 1);
    }

    private void createUser(String firstName, String lastName, String userName, boolean isTrainer){
        String uid = FirebaseAuth.getInstance().getUid();
        //(String userName, String firstName, String lastName, String uid, boolean isTrainer)
        //creating the userClass
        UserClass user = new UserClass(userName, firstName, lastName, uid, isTrainer);
    }
}