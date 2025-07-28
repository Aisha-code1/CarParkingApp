package com.example.carparkingapp;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ViewBookingActivity extends AppCompatActivity {

    RecyclerView rvViewBooking;
    ViewBookingAdapter adapter;
    List<ViewBooking> bookingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);

        rvViewBooking = findViewById(R.id.rv_viewbooking);
        rvViewBooking.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ViewBookingAdapter(bookingList, this);
        rvViewBooking.setAdapter(adapter);

        loadAllBookings();
    }

    private void loadAllBookings() {
        FirebaseDatabase.getInstance().getReference("Bookings")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        bookingList.clear();

                        for (DataSnapshot mallSnapshot : snapshot.getChildren()) {
                            for (DataSnapshot bookingSnapshot : mallSnapshot.getChildren()) {
                                ViewBooking booking = bookingSnapshot.getValue(ViewBooking.class);
                                if (booking != null) {
                                    bookingList.add(booking);
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ViewBookingActivity.this,
                                "Failed to load bookings: " + error.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
