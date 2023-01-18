package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding =
        setContentView(R.layout.activity_main);
        bottomNavView = findViewById(R.id.bottomNavView);
        replaceFragments(new RestaurantsFragment());
        bottomNavView.setOnItemSelectedListener(item ->{
            if(item.getItemId() == R.id.restaurants){
                replaceFragments(new RestaurantsFragment());
            }
            else if(item.getItemId() == R.id.account){
                replaceFragments(new AccountFragment());
            }
            else if(item.getItemId() == R.id.cart){
                replaceFragments(new CartFragment());
            }
            return true;
        });

    }
    private void replaceFragments(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.replaceableLayout, fragment);
        fragmentTransaction.commit();
    }
}