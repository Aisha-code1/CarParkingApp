package com.example.carparkingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class BookingActivity extends AppCompatActivity {
    String mallId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        EditText edttype = findViewById(R.id.vehicle_type);
        EditText  edtno = findViewById(R.id.vehicle_no);
        EditText edtdays = findViewById(R.id.days);
        Button booking = findViewById(R.id.book);
        mallId = getIntent().getStringExtra("mallId");
        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = edttype.getText().toString();
                String no = edtno.getText().toString();
                String days = edtdays.getText().toString();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (type.isEmpty()){
                    Toast.makeText(BookingActivity.this, "Enter vehicle type", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (no.isEmpty()){
                    Toast.makeText(BookingActivity.this, "Enter vehicle no.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (days.isEmpty()){
                    Toast.makeText(BookingActivity.this, "Enter no. of days", Toast.LENGTH_SHORT).show();
                    return;
                }
                Booking bookings = new Booking();
                bookings.userId = userId;
                bookings.vehicleNumber = no;
                bookings.vehicleType = type;
                bookings.days = days;
                FirebaseDatabase.getInstance().getReference("Bookings")
                        .child(mallId)
                        .push()
                        .setValue(bookings);
                Toast.makeText(BookingActivity.this, "Booking successfull", Toast.LENGTH_SHORT).show();
                finish();
            }

        });

    }
}