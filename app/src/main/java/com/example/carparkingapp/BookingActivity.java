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

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class BookingActivity extends AppCompatActivity {
    String mallId;
    String mallName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        EditText edttype = findViewById(R.id.vehicle_type);
        EditText  edtno = findViewById(R.id.vehicle_no);

        EditText edtcontact = findViewById(R.id.contact);

        Button booking = findViewById(R.id.book);
        mallId = getIntent().getStringExtra("mallId");
        mallName = getIntent().getStringExtra("mallName");
        EditText edtdate = findViewById(R.id.date);
        edtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(BookingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                                String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                                edtdate.setText(selectedDate);
                            }
                        }, year, month, day);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = edttype.getText().toString();
                String no = edtno.getText().toString();
                String date = edtdate.getText().toString();
                String contact = edtcontact.getText().toString();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (type.isEmpty()) {
                    Toast.makeText(BookingActivity.this, "Enter vehicle type", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (no.isEmpty()) {
                    Toast.makeText(BookingActivity.this, "Enter vehicle no.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (date.isEmpty()) {
                    Toast.makeText(BookingActivity.this, "Enter date", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (contact.isEmpty()) {
                    Toast.makeText(BookingActivity.this, "Enter contact no.", Toast.LENGTH_SHORT).show();
                    return;
                }



                mallName = getIntent().getStringExtra("mallName");

                if (mallName == null) {
                    Toast.makeText(BookingActivity.this, "Mall name is missing!", Toast.LENGTH_SHORT).show();
                    return;
                }
                                Booking bookings = new Booking();
                                bookings.userId = userId;
                                bookings.mallId = mallId;
                                bookings.mallName = mallName;
                                bookings.vehicleNumber = no;
                                bookings.vehicleType = type;
                                bookings.date = date;
                                bookings.ContactNo = contact;
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