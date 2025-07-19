package com.example.carparkingapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class AddBookingActivity extends AppCompatActivity {

    EditText etName, etVehicleType, etVehicleNo, etContact, etDate;
    Button btnAddBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking); // replace with actual XML file name


        etName = findViewById(R.id.et_Name);
        etVehicleType = findViewById(R.id.et_vehicle_type);
        etVehicleNo = findViewById(R.id.et_vehicle_no);
        etContact = findViewById(R.id.contact);
        etDate = findViewById(R.id.date);
        btnAddBooking = findViewById(R.id.btn_add_booking);

        btnAddBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBooking();
            }
        });
    }

    private void addBooking() {
        String mallName = etName.getText().toString().trim();
        String vehicleType = etVehicleType.getText().toString().trim();
        String vehicleNo = etVehicleNo.getText().toString().trim();
        String contactNo = etContact.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String userId = FirebaseAuth.getInstance().getUid();

        if (TextUtils.isEmpty(mallName) || TextUtils.isEmpty(vehicleType)
                || TextUtils.isEmpty(vehicleNo) || TextUtils.isEmpty(contactNo)
                || TextUtils.isEmpty(date)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        String id = FirebaseDatabase.getInstance().getReference("Bookings").push().getKey();


        ViewBooking booking = new ViewBooking();
        booking.setId(id);
        booking.setUserId(userId);
        booking.setMallName(mallName);
        booking.setVehicleType(vehicleType);
        booking.setVehicleNumber(vehicleNo);
        booking.setContactNo(contactNo);
        booking.setDate(date);

        FirebaseDatabase.getInstance().getReference("Bookings")
                .child(id)
                .setValue(booking)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(AddBookingActivity.this, "Booking added", Toast.LENGTH_SHORT).show();
                    finish(); // Optional: close activity
                })
                .addOnFailureListener(e -> Toast.makeText(AddBookingActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}