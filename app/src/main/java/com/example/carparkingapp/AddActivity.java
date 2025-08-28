package com.example.carparkingapp;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    EditText etName, etHourlyPrice, etDailyPrice, etCity, etTiming, etAddress;
    Button save;
    boolean isEdit = false;
    String editId = null;

    int openingHour, openingMinute, closingHour, closingMinute;
    boolean isOpeningSelected = false, isClosingSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etName = findViewById(R.id.et_Name);
        etHourlyPrice = findViewById(R.id.et_Price);
        etDailyPrice = findViewById(R.id.edt_Price);
        etCity = findViewById(R.id.et_city);
        etTiming = findViewById(R.id.et_timing);
        etAddress = findViewById(R.id.et_address);
        save = findViewById(R.id.btn_save);

        etTiming.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog openingDialog = new TimePickerDialog(AddActivity.this,
                    (view, oHour, oMinute) -> {
                        openingHour = oHour;
                        openingMinute = oMinute;
                        isOpeningSelected = true;

                        String openingTime = formatTime12Hour(oHour, oMinute);

                        TimePickerDialog closingDialog = new TimePickerDialog(AddActivity.this,
                                (view2, cHour, cMinute) -> {
                                    closingHour = cHour;
                                    closingMinute = cMinute;
                                    isClosingSelected = true;

                                    if (closingHour < openingHour ||
                                            (closingHour == openingHour && closingMinute <= openingMinute)) {
                                        Toast.makeText(AddActivity.this,
                                                "Closing time must be after Opening time",
                                                Toast.LENGTH_SHORT).show();
                                        etTiming.setText("");
                                        return;
                                    }

                                    String closingTime = formatTime12Hour(cHour, cMinute);
                                    etTiming.setText(openingTime + " - " + closingTime);
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
            etHourlyPrice.setText(String.valueOf(getIntent().getIntExtra("hourlyPrice", 0)));
            etDailyPrice.setText(String.valueOf(getIntent().getIntExtra("dailyPrice", 0)));
            etCity.setText(getIntent().getStringExtra("city"));
            etTiming.setText(getIntent().getStringExtra("timing"));
            etAddress.setText(getIntent().getStringExtra("address"));
            save.setText("Update");
        }

        save.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String hourlyPriceStr = etHourlyPrice.getText().toString().trim();
            String dailyPriceStr = etDailyPrice.getText().toString().trim();
            String city = etCity.getText().toString().trim();
            String timing = etTiming.getText().toString().trim();
            String address = etAddress.getText().toString().trim();

            // Name Validation
            if (name.isEmpty()) {
                etName.setError("Enter mall name");
                etName.requestFocus();
                return;
            }

            // Hourly Price Validation
            if (hourlyPriceStr.isEmpty()) {
                etHourlyPrice.setError("Enter Hourly Price");
                etHourlyPrice.requestFocus();
                return;
            }

            // Daily Price Validation
            if (dailyPriceStr.isEmpty()) {
                etDailyPrice.setError("Enter Daily Price");
                etDailyPrice.requestFocus();
                return;
            }

            // City Validation
            if (city.isEmpty()) {
                etCity.setError("Enter city");
                etCity.requestFocus();
                return;
            }
            if (city.length() < 3 || city.length() > 20) {
                etCity.setError("City must be 3-20 characters");
                etCity.requestFocus();
                return;
            }
            city = city.substring(0, 1).toUpperCase() + city.substring(1);

            // Address Validation
            if (address.isEmpty()) {
                etAddress.setError("Enter address");
                etAddress.requestFocus();
                return;
            }
            if (address.length() < 5 || address.length() > 50) {
                etAddress.setError("Address must be 5-50 characters");
                etAddress.requestFocus();
                return;
            }
            address = address.substring(0, 1).toUpperCase() + address.substring(1);

            // Timing Validation
            if (timing.isEmpty()) {
                etTiming.setError("Please select opening and closing time");
                etTiming.requestFocus();
                return;
            }

            int hourlyPrice, dailyPrice;
            try {
                hourlyPrice = Integer.parseInt(hourlyPriceStr);
                dailyPrice = Integer.parseInt(dailyPriceStr);
            } catch (NumberFormatException e) {
                etHourlyPrice.setError("Enter valid number");
                etDailyPrice.setError("Enter valid number");
                return;
            }

            // Price Limits
            if (hourlyPrice < 50 || hourlyPrice > 1000) {
                etHourlyPrice.setError("Hourly Price must be between 50 and 1000");
                etHourlyPrice.requestFocus();
                return;
            }
            if (dailyPrice < 100 || dailyPrice > 5000) {
                etDailyPrice.setError("Daily Price must be between 100 and 5000");
                etDailyPrice.requestFocus();
                return;
            }
            if (hourlyPrice >= dailyPrice) {
                etHourlyPrice.setError("Hourly Price must be less than Daily Price");
                etHourlyPrice.requestFocus();
                return;
            }

            Manage manage = new Manage(name, hourlyPrice, dailyPrice, city, timing, address);

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
        });
    }

    private String formatTime12Hour(int hourOfDay, int minute) {
        String amPm = (hourOfDay >= 12) ? "PM" : "AM";
        int hour = hourOfDay % 12;
        if (hour == 0) hour = 12;
        return String.format("%02d:%02d %s", hour, minute, amPm);
    }
}