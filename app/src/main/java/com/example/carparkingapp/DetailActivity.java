package com.example.carparkingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {
    TextView tvName, tvPrice, tvCity, tvTiming, tvAddress;
    String uuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvName = findViewById(R.id.tv_detail_name);
        tvPrice = findViewById(R.id.tv_detail_price);
        tvCity = findViewById(R.id.tv_detail_city);
        tvTiming = findViewById(R.id.tv_detail_timing);
        tvAddress = findViewById(R.id.tv_detail_address);

        Button book = findViewById(R.id.btn_book);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, BookingActivity.class);
                intent.putExtra("mallId", uuid);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        uuid = intent.getStringExtra("uuid");
        tvName.setText(intent.getStringExtra("name"));
        int price = intent.getIntExtra("price", 0);
        tvPrice.setText("Price: Rs " + price);
        tvCity.setText("City: " + intent.getStringExtra("city"));
        tvTiming.setText("Timing: " + intent.getStringExtra("timing"));
        tvAddress.setText("Address: " + intent.getStringExtra("address"));
    }
}