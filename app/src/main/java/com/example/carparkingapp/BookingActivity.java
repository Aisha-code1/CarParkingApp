package com.example.carparkingapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;



    public class BookingActivity extends AppCompatActivity {
        String mallId;
        String mallName;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_booking);
            EditText edttype = findViewById(R.id.vehicle_type);
            EditText edtno = findViewById(R.id.vehicle_no);
            Button booking = findViewById(R.id.book);

            mallId = getIntent().getStringExtra("mallId");
            mallName = getIntent().getStringExtra("mallName");
            booking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String type = edttype.getText().toString();
                    String no = edtno.getText().toString();

                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    if (type.isEmpty()) {
                        Toast.makeText(com.example.carparkingapp.BookingActivity.this, "Enter vehicle type", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (no.isEmpty()) {
                        Toast.makeText(com.example.carparkingapp.BookingActivity.this, "Enter vehicle no.", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    mallName = getIntent().getStringExtra("mallName");

                    if (mallName == null) {
                        Toast.makeText(com.example.carparkingapp.BookingActivity.this, "Mall name is missing!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Booking bookings = new Booking();
                    bookings.userId = userId;
                    bookings.mallId = mallId;
                    bookings.mallName = mallName;
                    bookings.vehicleNumber = no;
                    bookings.vehicleType = type;
                    FirebaseDatabase.getInstance().getReference("Bookings")
                            .child(mallId)
                            .push()
                            .setValue(bookings);
                    Toast.makeText(com.example.carparkingapp.BookingActivity.this, "Booking successfull", Toast.LENGTH_SHORT).show();
                    finish();

                }
            });
        }
    }