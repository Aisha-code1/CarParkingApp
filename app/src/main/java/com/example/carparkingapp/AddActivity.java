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

    EditText etName, etPrice, edtPrice, etCity, etTiming, etAddress;
    Button save;
    boolean isEdit = false;
    String editId = null;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        etName = findViewById(R.id.et_Name);
        etPrice = findViewById(R.id.et_Price);
        edtPrice = findViewById(R.id.edt_Price);
        etCity = findViewById(R.id.et_city);
        etTiming = findViewById(R.id.et_timing);
        etAddress = findViewById(R.id.et_address);
        save = findViewById(R.id.btn_save);

        etTiming.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog openingDialog = new TimePickerDialog(AddActivity.this,
                    (view, openingHour, openingMinute) -> {

                        String openingTime = formatTime12Hour(openingHour, openingMinute);

                        TimePickerDialog closingDialog = new TimePickerDialog(AddActivity.this,
                                (view2, closingHour, closingMinute) -> {
                                    String closingTime = formatTime12Hour(closingHour, closingMinute);
                                    String finalTiming =  ( openingTime + " - " + closingTime);
                                    etTiming.setText(finalTiming);
                                },
                                hour, minute, false);

                        closingDialog.setTitle("Select Closing Time");
                        closingDialog.show();

                    },
                    hour, minute, false);

            openingDialog.setTitle("Select Opening Time");
            openingDialog.show();
        });
        if (getIntent().getBooleanExtra("edit_mode", false)) {
            isEdit = true;
            editId = getIntent().getStringExtra("id");
            etName.setText(getIntent().getStringExtra("name"));
            etPrice.setText(String.valueOf(getIntent().getStringExtra("price")));
            etCity.setText(getIntent().getStringExtra("city"));
            etTiming.setText(getIntent().getStringExtra("timing"));
            etAddress.setText(getIntent().getStringExtra("address"));
            save.setText("Update");
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String priceStr = etPrice.getText().toString().trim();
                String pricestr = edtPrice.getText().toString().trim();
                String city = etCity.getText().toString().trim();
                String timing = etTiming.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                if (name.isEmpty()) {
                    etName.setError("Enter mall name");
                    etName.requestFocus();
                    return;
                }
                if (priceStr.isEmpty()) {
                    etPrice.setError("Enter Hourly Price");
                    etPrice.requestFocus();
                    return;
                }
                if (pricestr.isEmpty()) {
                    etPrice.setError("Enter Daily Price");
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


                int price, Rs;
                try {
                    price = Integer.parseInt(priceStr);
                    Rs = Integer.parseInt(pricestr);
                } catch (NumberFormatException e) {
                    etPrice.setError("Enter valid number");
                    etPrice.requestFocus();
                    edtPrice.setError("Enter valid number");
                    edtPrice.requestFocus();
                    return;
                }
                Manage manage = new Manage(name, price, Rs, city, timing, address);

                if (isEdit) {
                    FirebaseDatabase.getInstance().getReference("Manage")
                            .child(editId)
                            .setValue(manage);
                    Toast.makeText(AddActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseDatabase.getInstance().getReference("Manage")
                            .push()
                            .setValue(manage);
                    Toast.makeText(AddActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

    private String formatTime12Hour(int hourOfDay, int minute) {
        String amPm = (hourOfDay >= 12) ? "PM" : "AM";
        int hour = hourOfDay % 12;
        if (hour == 0) hour = 12;
        return String.format("%02d:%02d %s", hour, minute, amPm);
    }
}
