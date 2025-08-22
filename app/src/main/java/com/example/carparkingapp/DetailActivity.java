package com.example.carparkingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    TextView tvName, tvHourly, tvDaily, tvCity, tvTiming, tvAddress;
    String id, mallName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        tvName = findViewById(R.id.tv_detail_name);
        tvHourly = findViewById(R.id.tv_detail_hourly);
        tvDaily = findViewById(R.id.tv_detail_daily);
        tvCity = findViewById(R.id.tv_detail_city);
        tvTiming = findViewById(R.id.tv_detail_timing);
        tvAddress = findViewById(R.id.tv_detail_address);
        Button book = findViewById(R.id.btn_book);

        Intent intent = getIntent();
        mallName = intent.getStringExtra("mallName");
        id = intent.getStringExtra("id");

        String city = intent.getStringExtra("city");
        String timing = intent.getStringExtra("timing");
        String address = intent.getStringExtra("address");
        int hourlyPrice = intent.getIntExtra("hourlyPrice", 0);
        int dailyPrice = intent.getIntExtra("dailyPrice", 0);


        tvName.setText(mallName);
        tvHourly.setText("Hourly Price: Rs " + hourlyPrice);
        tvDaily.setText("Daily Price: Rs " + dailyPrice);
        tvCity.setText("City: " + city);
        tvTiming.setText("Timing: " + timing);
        tvAddress.setText("Address: " + address);


        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookIntent = new Intent(DetailActivity.this, BookingActivity.class);
                bookIntent.putExtra("mallId", id);
                bookIntent.putExtra("mallName", mallName);
                startActivity(bookIntent);
            }
        });
    }
}
