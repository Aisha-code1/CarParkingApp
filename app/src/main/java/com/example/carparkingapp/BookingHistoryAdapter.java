package com.example.carparkingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }
}
