package com.example.carparkingapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class BookingActivity extends AppCompatActivity {
    String mallId, mallName;
    RadioGroup bookingTypeGroup;
    Button booking, pay;
    TextView dateLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        EditText edttype = findViewById(R.id.vehicle_type);
        EditText edtno = findViewById(R.id.vehicle_no);
        EditText edtdate = findViewById(R.id.date);
        EditText edtcontact = findViewById(R.id.contact);
        booking = findViewById(R.id.book);
        pay = findViewById(R.id.pay);
        bookingTypeGroup = findViewById(R.id.bookingTypeGroup);
        dateLabel = findViewById(R.id.datelabel);

        edtdate.setOnClickListener(v -> {
            if (bookingTypeGroup.getCheckedRadioButtonId() == R.id.rbDaily) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog startPicker = new DatePickerDialog(BookingActivity.this,
                        (view, y, m, d) -> {
                            String startDate = d + "/" + (m + 1) + "/" + y;
                            Calendar minEnd = Calendar.getInstance();
                            minEnd.set(y, m, d);

                            DatePickerDialog endPicker = new DatePickerDialog(BookingActivity.this,
                                    (view2, y2, m2, d2) -> {
                                        String endDate = d2 + "/" + (m2 + 1) + "/" + y2;
                                        edtdate.setText(startDate + " to " + endDate);
                                    }, y, m, d);
                            endPicker.getDatePicker().setMinDate(minEnd.getTimeInMillis());
                            endPicker.show();
                        }, year, month, day);
                startPicker.getDatePicker().setMinDate(System.currentTimeMillis());
                startPicker.show();
            } else {
                Toast.makeText(this, "Hourly booking doesn't need date selection", Toast.LENGTH_SHORT).show();
            }
        });

        mallId = getIntent().getStringExtra("mallId");
        mallName = getIntent().getStringExtra("mallName");

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = edttype.getText().toString();
                String no = edtno.getText().toString();
                String date = edtdate.getText().toString();
                String contact = edtcontact.getText().toString();
                String bookingType = (bookingTypeGroup.getCheckedRadioButtonId() == R.id.rbDaily) ? "Daily" : "Hourly";
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                if (type.isEmpty()) {
                    Toast.makeText(BookingActivity.this, "Enter vehicle type", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (no.isEmpty()) {
                    Toast.makeText(BookingActivity.this, "Enter vehicle no.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (bookingType.equals("Daily") && date.isEmpty()) {
                    Toast.makeText(BookingActivity.this, "Enter date", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (contact.isEmpty()) {
                    Toast.makeText(BookingActivity.this, "Enter contact", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mallName == null) {
                    Toast.makeText(BookingActivity.this, "Mall name is missing!", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Bookings").child(mallId);
                String bookingId = ref.push().getKey();

                Booking bookings = new Booking(
                        bookingId,
                        userId,
                        type,
                        no,
                        bookingType.equals("Hourly") ? "Hourly Booking" : date,
                        mallName,
                        contact,
                        bookingType,
                        "Pending"
                );
                bookings.mallId = mallId;

                ref.child(bookingId).setValue(bookings);

                Toast.makeText(BookingActivity.this, "Booking successful", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}