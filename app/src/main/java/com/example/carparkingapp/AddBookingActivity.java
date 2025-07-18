package com.example.carparkingapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

public class AddBookingActivity extends AppCompatActivity {

    EditText etMallName, etVehicleType, etVehicleNo, etContactNo;
    Button btnAddBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking_actvity); 

        etMallName = findViewById(R.id.et_Name); // mall name
        etVehicleType = findViewById(R.id.et_vehicle_type);
        etVehicleNo = findViewById(R.id.et_vehicle_no);
        etContactNo = findViewById(R.id.et_contact_no);
        btnAddBooking = findViewById(R.id.btn_add_booking);

        btnAddBooking.setOnClickListener(v -> addBooking());
    }

    private void addBooking() {
        String mallName = etMallName.getText().toString().trim();
        String vehicleType = etVehicleType.getText().toString().trim();
        String vehicleNo = etVehicleNo.getText().toString().trim();
        String contactNo = etContactNo.getText().toString().trim();

        if (TextUtils.isEmpty(mallName) || TextUtils.isEmpty(vehicleType)
                || TextUtils.isEmpty(vehicleNo) || TextUtils.isEmpty(contactNo)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String bookingId = FirebaseDatabase.getInstance().getReference("Bookings").push().getKey();
        ViewBooking booking = new ViewBooking(bookingId, mallName, vehicleType, vehicleNo, contactNo);

        FirebaseDatabase.getInstance().getReference("Bookings")
                .child(bookingId)
                .setValue(booking)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Booking added successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void clearFields() {
        etMallName.setText("");
        etVehicleType.setText("");
        etVehicleNo.setText("");
        etContactNo.setText("");
    }
}


