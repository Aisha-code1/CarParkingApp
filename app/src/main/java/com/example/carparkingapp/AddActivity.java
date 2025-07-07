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
        EditText etAddress = findViewById(R.id.et_address);
        Button save = findViewById(R.id.btn_save);


        etTiming.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog openingDialog = new TimePickerDialog(AddActivity.this,
                    (view, openingHour, openingMinute) -> {

                        String openingTime = String.format("%02d:%02d", openingHour, openingMinute);

                        TimePickerDialog closingDialog = new TimePickerDialog(AddActivity.this,
                                (view2, closingHour, closingMinute) -> {
                                    String closingTime = String.format("%02d:%02d", closingHour, closingMinute);
                                    String finalTiming = "Timing: " + openingTime + " - " + closingTime;
                                    etTiming.setText(finalTiming);
                                },
                                hour, minute, true);

                        closingDialog.setTitle("Select Closing Time");
                        closingDialog.show();

                    },
                    hour, minute, true);

            openingDialog.setTitle("Select Opening Time");
            openingDialog.show();
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String priceStr = etPrice.getText().toString().trim();
                String city = etCity.getText().toString().trim();
                String timing= etTiming.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
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
                    etTiming.setError("Please select opening and closing time");
                    etTiming.requestFocus();
                    return;
                }
                if (address.isEmpty()) {
                    etAddress.setError("Enter address");
                    etAddress.requestFocus();
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
                manage.price = price;
                manage.address = address;
                manage.timing = timing;
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