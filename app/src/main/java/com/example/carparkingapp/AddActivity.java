package com.example.carparkingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);
        EditText etName = findViewById(R.id.et_Name);
        EditText etPrice = findViewById(R.id.et_Price);
        EditText etCity = findViewById(R.id.et_city);
        EditText etTiming = findViewById(R.id.et_timing);
        Button save = findViewById(R.id.btn_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String price = etPrice.getText().toString();
                String city = etCity.getText().toString();
                String timing = etTiming.getText().toString();
                Manage manage = new Manage();
                manage.name = name;
                manage.city = city;
                manage.timing = timing;
               manage.price = Integer.parseInt(price);
                //save data
                FirebaseDatabase.getInstance()
                        .getReference("Manage")
//                        .child(FirebaseAuth.getInstance().getUid())
                        .push()
                        .setValue(manage);
            }


        });
    }
}