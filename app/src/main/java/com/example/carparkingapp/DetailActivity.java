package com.example.carparkingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {
    TextView tvName, tvPrice, tvCity, tvTiming, tvAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvName = findViewById(R.id.tv_detail_name);
        tvPrice = findViewById(R.id.tv_detail_price);
        tvCity = findViewById(R.id.tv_detail_city);
        tvTiming = findViewById(R.id.tv_detail_timing);
        tvAddress = findViewById(R.id.tv_detail_address);

        Intent intent = getIntent();
        tvName.setText(intent.getStringExtra("name"));
        int price = intent.getIntExtra("price", 0);
        tvPrice.setText("Price: Rs " + price);

        tvCity.setText("City: " + intent.getStringExtra("city"));
        tvTiming.setText("Timing: " + intent.getStringExtra("timing"));
        tvAddress.setText("Address: " + intent.getStringExtra("address"));
    }
}