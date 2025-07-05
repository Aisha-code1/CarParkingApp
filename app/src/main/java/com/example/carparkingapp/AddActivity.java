package com.example.carparkingapp;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

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
        etTiming.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    AddActivity.this,
                    (view, hourOfDay, minute1) -> {
                        String formattedTime = String.format("%02d:%02d", hourOfDay, minute1);
                        etTiming.setText(formattedTime);
                    },
                    hour, minute, true
            );
            timePickerDialog.show();
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String priceStr = etPrice.getText().toString().trim();
                String city = etCity.getText().toString().trim();
                String timing = etTiming.getText().toString().trim();
                if (name.isEmpty()) {
                    etName.setError("Enter mall name");
                    etName.requestFocus();
                    return;
                }
                if (priceStr.isEmpty()) {
                    etPrice.setError("Enter price");
                    etPrice.requestFocus();
                    return;
                }
                if (city.isEmpty()) {
                    etCity.setError("Enter city");
                    etCity.requestFocus();
                    return;
                }
                if (timing.isEmpty()) {
                    etTiming.setError("Select timing");
                    etTiming.requestFocus();
                    return;
                }

                int price;
                try {
                    price = Integer.parseInt(priceStr);
                }
                catch (NumberFormatException e) {
                    etPrice.setError("Enter valid number");
                    etPrice.requestFocus();
                    return;
                }

                Manage manage = new Manage();
                manage.name = name;
                manage.city = city;
                manage.timing = timing;
                manage.price = price;

                FirebaseDatabase.getInstance()
                        .getReference("Manage")
                        .push()
                        .setValue(manage);
                Toast.makeText(AddActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                finish();
            }


        });
    }
}