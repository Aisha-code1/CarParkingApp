package com.example.carparkingapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ViewBookingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ViewBookingAdapter adapter;
    List<ViewBooking> bookingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);

        recyclerView = findViewById(R.id.rv_viewbooking);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookingList = new ArrayList<>();
        adapter = new ViewBookingAdapter(bookingList, this);
        recyclerView.setAdapter(adapter);

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
                        Toast.makeText(ViewBookingActivity.this, "Failed to load bookings", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
