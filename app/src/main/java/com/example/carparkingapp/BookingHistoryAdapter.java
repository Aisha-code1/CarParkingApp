package com.example.carparkingapp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingViewHolder> {

    List<Booking> bookingList;
    Context context;

    public BookingHistoryAdapter(List<Booking> bookingList, Context context) {
        this.bookingList = bookingList;
        this.context = context;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booking_history_item, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        holder.tvMallName.setText("Mall: " + booking.mallName);
        holder.tvVehicleType.setText("Type: " + booking.vehicleType);
        holder.tvVehicleNumber.setText("Number: " + booking.vehicleNumber);
        holder.tvdate.setText("Date: " + booking.date);
        holder.tvContactNo.setText("Contact: " + booking.ContactNo);
        holder.tvBookingType.setText("Booking Type: " + booking.bookingType);
        holder.tvStatus.setText("Status: " + booking.status);
        boolean isPast = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date bookingDate = sdf.parse(booking.date);
            if (bookingDate.before(new Date())) {
                isPast = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!isPast && !"Cancelled".equalsIgnoreCase(booking.status)) {
            holder.btnCancel.setVisibility(View.VISIBLE);
        } else {
            holder.btnCancel.setVisibility(View.GONE);
        }

        holder.btnCancel.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Cancel Booking")
                    .setMessage("Are you sure you want to cancel this booking?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                         FirebaseDatabase.getInstance()
                                .getReference("Bookings")
                                .child(booking.mallId) // assuming mallId stored in booking
                                .child(booking.id)
                                 .child("status")
                                 .setValue("Cancelled").addOnSuccessListener(aVoid -> {
                                     booking.status = "Cancelled"; // local update
                                    notifyDataSetChanged();
                                     Toast.makeText(context, "Booking Cancelled", Toast.LENGTH_SHORT).show();
                                 })
                                 .addOnFailureListener(e ->
                                         Toast.makeText(context, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }
}
