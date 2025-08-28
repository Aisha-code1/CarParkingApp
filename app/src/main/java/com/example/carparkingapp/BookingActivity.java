package com.example.carparkingapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import java.util.HashMap;

public class BookingActivity extends AppCompatActivity {
    String mallId, mallName;
    RadioGroup bookingTypeGroup;
    Button booking;
    TextView dateLabel;
    EditText edttype, edtno, edtdate, edtcontact, edttime; // add edttime for hourly

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        edttype = findViewById(R.id.vehicle_type);
        edtno = findViewById(R.id.vehicle_no);
        edtdate = findViewById(R.id.date);
        edtcontact = findViewById(R.id.contact);
        booking = findViewById(R.id.book);
        bookingTypeGroup = findViewById(R.id.bookingTypeGroup);
        dateLabel = findViewById(R.id.datelabel);

        // Hourly time field
        edttime = findViewById(R.id.time);
        edttime.setVisibility(View.GONE);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Load profile info
        FirebaseDatabase.getInstance().getReference("Users").child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            if (snapshot.hasChild("vehicleType")) {
                                edttype.setText(snapshot.child("vehicleType").getValue(String.class));
                            }
                            if (snapshot.hasChild("vehicleNumber")) {
                                edtno.setText(snapshot.child("vehicleNumber").getValue(String.class));
                            }
                            if (snapshot.hasChild("contact")) {
                                edtcontact.setText(snapshot.child("contact").getValue(String.class));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });

        // Toggle UI based on booking type
        bookingTypeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbDaily) {
                edtdate.setVisibility(View.VISIBLE);
                edttime.setVisibility(View.GONE);
            } else if (checkedId == R.id.rbHourly) {
                edtdate.setVisibility(View.GONE);
                edttime.setVisibility(View.VISIBLE);
            }
        });

        // Date picker for daily booking
        edtdate.setOnClickListener(v -> {
            if (bookingTypeGroup.getCheckedRadioButtonId() == R.id.rbDaily) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog startPicker = new DatePickerDialog(this,
                        (view, y, m, d) -> {
                            String startDate = d + "/" + (m + 1) + "/" + y;

                            Calendar minEnd = Calendar.getInstance();
                            minEnd.set(y, m, d);

                            DatePickerDialog endPicker = new DatePickerDialog(this,
                                    (view2, y2, m2, d2) -> {
                                        String endDate = d2 + "/" + (m2 + 1) + "/" + y2;
                                        edtdate.setText(startDate + " to " + endDate);
                                    }, y, m, d);
                            endPicker.getDatePicker().setMinDate(minEnd.getTimeInMillis());
                            endPicker.show();
                        }, year, month, day);
                startPicker.getDatePicker().setMinDate(System.currentTimeMillis());
                startPicker.show();
            }
        });

        // Time picker for hourly booking
        edttime.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();
            int hour = now.get(Calendar.HOUR_OF_DAY);
            int minute = now.get(Calendar.MINUTE);

            TimePickerDialog timePicker = new TimePickerDialog(this, (view, h, m) -> {
                Calendar selectedTime = Calendar.getInstance();
                selectedTime.set(Calendar.HOUR_OF_DAY, h);
                selectedTime.set(Calendar.MINUTE, m);

                if (selectedTime.before(Calendar.getInstance())) {
                    Toast.makeText(this, "Cannot select past time", Toast.LENGTH_SHORT).show();
                } else {
                    edttime.setText(String.format("%02d:%02d", h, m));
                }
            }, hour, minute, true);

            timePicker.show();
        });

        mallId = getIntent().getStringExtra("mallId");
        mallName = getIntent().getStringExtra("mallName");

        booking.setOnClickListener(v -> {
            String type = edttype.getText().toString();
            String no = edtno.getText().toString();
            String dateOrTime = (bookingTypeGroup.getCheckedRadioButtonId() == R.id.rbDaily)
                    ? edtdate.getText().toString()
                    : edttime.getText().toString();
            String contact = edtcontact.getText().toString();
            String bookingType = (bookingTypeGroup.getCheckedRadioButtonId() == R.id.rbDaily) ? "Daily" : "Hourly";

            if (type.isEmpty()) {
                Toast.makeText(this, "Enter vehicle type", Toast.LENGTH_SHORT).show();
                return;
            }
            if (no.isEmpty()) {
                Toast.makeText(this, "Enter vehicle no.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (bookingType.equals("Daily") && dateOrTime.isEmpty()) {
                Toast.makeText(this, "Enter date", Toast.LENGTH_SHORT).show();
                return;
            }
            if (bookingType.equals("Hourly") && dateOrTime.isEmpty()) {
                Toast.makeText(this, "Select time", Toast.LENGTH_SHORT).show();
                return;
            }
            if (contact.isEmpty()) {
                Toast.makeText(this, "Enter contact", Toast.LENGTH_SHORT).show();
                return;
            }

            // Update profile info
            FirebaseDatabase.getInstance().getReference("Users").child(userId).child("vehicleType").setValue(type);
            FirebaseDatabase.getInstance().getReference("Users").child(userId).child("vehicleNumber").setValue(no);
            FirebaseDatabase.getInstance().getReference("Users").child(userId).child("contact").setValue(contact);

            // Save booking
            String bookingId = FirebaseDatabase.getInstance()
                    .getReference("Bookings").child(mallId).push().getKey();

            Booking bookings = new Booking(
                    bookingId,
                    userId,
                    type,
                    no,
                    dateOrTime,
                    mallName,
                    contact,
                    bookingType,
                    "Pending"
            );
            bookings.mallId = mallId;

            FirebaseDatabase.getInstance()
                    .getReference("Bookings").child(mallId).child(bookingId)
                    .setValue(bookings);

            Toast.makeText(this, "Booking successful", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
