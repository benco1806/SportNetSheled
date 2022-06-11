package com.example.sportnetsheled;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sportnetsheled.ui.CustomFragment;
import com.example.sportnetsheled.ui.ExploreFragment;
import com.example.sportnetsheled.ui.HomeFragment;
import com.example.sportnetsheled.ui.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//
    private CustomFragment homeFragment = null, exploreFragment = null, profileFragment = null;
    private boolean isSentToHome = false;

    public static UserClass USER;
    public static DatabaseReference USER_REFERENCE;
    public static PostManager postManager;

    private boolean issavedInstanceStatenull = false;
    BottomNavigationView bottomNav;
    MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(USER == null)
        {
            if (FirebaseAuth.getInstance().getCurrentUser() == null){
                startActivity(new Intent(this, WelcomingActivity.class));
                finish();
                return;
            }else{
                ProgressDialog pd = new ProgressDialog(this);
                pd.setMessage("loading");
                pd.setCancelable(false);
                pd.show();
                UserClass.lookForUser(FirebaseAuth.getInstance().getCurrentUser().getUid(), pd, this);
            }
        }

        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);



        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            issavedInstanceStatenull = true;
        }

        receiver = new MyReceiver();

    }

    protected void onStart() {
        registerReceiver(receiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        super.onStart();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.upp_menu, menu);
        return true;
    }

    @Override
    protected void onStop() {
        unregisterReceiver(receiver);
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if(item.getItemId() == R.id.itAddPost){
            addPost();
        }else {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, WelcomingActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
        }

    private void addPost() {
            startActivity(new Intent(this, PostPublisherActivity.class));
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        if(homeFragment == null)
                            homeFragment = new HomeFragment(R.layout.fragment_home, this);
                        selectedFragment = homeFragment;
                        break;
                    case R.id.nav_explore:
                        if(exploreFragment == null)
                            exploreFragment = new ExploreFragment(R.layout.fragment_home, this);
                        selectedFragment = exploreFragment;
                        break;
                    case R.id.nav_profile:
                        if(profileFragment == null)
                            profileFragment = new ProfileFragment(R.layout.fragment_profile, this);
                        selectedFragment = profileFragment;
                        break;
                }

                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            };

    public void onUserDataHasSynchronized(){

        if(issavedInstanceStatenull){
            postManager = new PostManager(MainActivity.this);
            homeFragment = new HomeFragment(R.layout.fragment_home, this);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
            bottomNav.setSelectedItemId(R.id.nav_home);
            issavedInstanceStatenull = false;
        }

        if(USER == null){
            Log.e("MAIN:onUserDataHasSynchronized","can not find the user: ");
            Toast.makeText(this, "MAIN:onUserDataHasSynchronized error", Toast.LENGTH_SHORT).show();
            System.exit(-1);
        }

        if(homeFragment == null)
            homeFragment = new HomeFragment(R.layout.fragment_home, this);
        if(exploreFragment == null)
            exploreFragment = new ExploreFragment(R.layout.fragment_home, this);
        if(profileFragment == null)
            profileFragment = new ProfileFragment(R.layout.fragment_profile, this);

        //Synchronizing user data for the fragments...
        profileFragment.onUserDataHasSynchronized();
        homeFragment.onUserDataHasSynchronized();
    }


}