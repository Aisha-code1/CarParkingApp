package com.example.carparkingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ViewBookingAdapter extends RecyclerView.Adapter<ViewBookingHolder> {

    List<ViewBooking> bookingList;
    Context context;

    public ViewBookingAdapter(List<ViewBooking> bookingList, Context context) {
        this.bookingList = bookingList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewBookingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booking_item, parent, false);
        return new ViewBookingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBookingHolder holder, int position) {
        ViewBooking booking = bookingList.get(position);

        holder.tvMallName.setText("Mall: " + booking.mallName);
        holder.tvVehicleType.setText("Vehicle Type: " + booking.vehicleType);
        holder.tvVehicleNo.setText("Vehicle No: " + booking.vehicleNumber);
        holder.tvDate.setText("Date: " + booking.date);
        holder.tvContactNo.setText("Contact No: " + booking.ContactNo);
        holder.tvBookingType.setText("Booking Type: " + booking.bookingType);
        holder.tvStatus.setText("Status: " + booking.status);


        if ("Pending".equalsIgnoreCase(booking.status)) {
            holder.btnConfirm.setVisibility(View.VISIBLE);
        } else {
            holder.btnConfirm.setVisibility(View.GONE);
        }

        holder.btnConfirm.setOnClickListener(v -> {
            if (booking.id == null || booking.mallId == null) {
                Toast.makeText(context, "Booking ID or Mall ID missing", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseDatabase.getInstance().getReference("Bookings")
                    .child(booking.mallId)
                    .child(booking.id)
                    .child("status")
                    .setValue("Confirmed")
                    .addOnSuccessListener(unused -> {
                        booking.status = "Confirmed";
                        notifyItemChanged(holder.getAdapterPosition());
                        Toast.makeText(context, "Booking Confirmed", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }
}
