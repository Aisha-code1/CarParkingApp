package com.example.carparkingapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carparkingapp.Fragment.AdminHomeFragment;
import com.example.carparkingapp.Fragment.HomeFragment;
import com.example.carparkingapp.Fragment.LocationFragment;
import com.example.carparkingapp.Fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BottomnavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomnavigation);
       BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
       bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               if (item.getItemId() == R.id.item_home) {
                   getSupportFragmentManager()
                           .beginTransaction()
                           .replace(R.id.fragment_container, new AdminHomeFragment())
                           .commit();
               }
                   else if (item.getItemId() == R.id.item_location) {

               } else if (item.getItemId() == R.id.item_profile) {

               }
               return false;
           }
       });
    }
    private void loadFragment(){

    }
}