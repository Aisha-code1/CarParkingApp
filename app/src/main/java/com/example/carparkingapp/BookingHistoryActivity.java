package com.example.carparkingapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.recaptcha.internal.zzbw;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookingHistoryActivity extends AppCompatActivity {
    RecyclerView rvHistory;
    BookingHistoryAdapter adapter;
    List<Booking> bookinglist = new ArrayList<>();
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);
        rvHistory = findViewById(R.id.rv_user_history);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookingHistoryAdapter(bookinglist, this);
        rvHistory.setAdapter(adapter);
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        loadUserBooking();

    }
    private void loadUserBooking(){
        FirebaseDatabase.getInstance().getReference("Bookings")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        bookinglist.clear();
                        for (DataSnapshot mallSnapshot : snapshot.getChildren()) {
                            for (DataSnapshot bookingSnapshot : mallSnapshot.getChildren()) {
                                Booking booking = bookingSnapshot.getValue(Booking.class);
                                if (booking != null && booking.userId.equals(currentUserId)) {
                                    bookinglist.add(booking);
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(BookingHistoryActivity.this, "Failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}