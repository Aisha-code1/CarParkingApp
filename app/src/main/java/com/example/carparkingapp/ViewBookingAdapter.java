package com.example.carparkingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

        holder.tvUserId.setText("User ID: " + booking.getUserId());
        holder.tvVehicleType.setText("Vehicle Type: " + booking.getVehicleType());
        holder.tvVehicleNo.setText("Vehicle No: " + booking.getVehicleNumber());
        holder.tvDays.setText("Days: " + booking.getDays());
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }
}
