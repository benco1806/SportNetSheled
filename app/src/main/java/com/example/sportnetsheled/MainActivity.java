package com.example.sportnetsheled;

import android.content.Intent;
import android.os.Bundle;

import com.example.sportnetsheled.ui.ExploreFragment;
import com.example.sportnetsheled.ui.HomeFragment;
import com.example.sportnetsheled.ui.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sportnetsheled.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    private Fragment homeFragment = null, exploreFragment = null, profileFragment = null;
    private UserClass user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(user == null)
        {
            if (FirebaseAuth.getInstance().getCurrentUser() == null){
                startActivityForResult(new Intent(this, WelcomingActivity.class), UserClass.REQUEST_CODE);
            }
        }


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);



        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            homeFragment = new HomeFragment(R.layout.fragment_home, this);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
            bottomNav.setSelectedItemId(R.id.nav_home);
        }

//            View home = findViewById(R.id.nav_home);
//            home.performClick();



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


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
                            exploreFragment = new ExploreFragment(R.layout.fragment_explore, this);
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

}