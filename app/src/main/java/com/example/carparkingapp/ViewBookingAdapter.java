package com.example.carparkingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        ViewBooking viewbooking = bookingList.get(position);

        holder.userId.setText("User ID: " + (viewbooking.getUserId() != null ? viewbooking.getUserId() : "N/A"));
        holder.tvMallName.setText("Mall name: " + (viewbooking.getMallName() != null ? viewbooking.getMallName() : "N/A"));
        holder.tvVehicleType.setText("Vehicle Type: " + (viewbooking.getVehicleType() != null ? viewbooking.getVehicleType() : "N/A"));
        holder.tvVehicleNo.setText("Vehicle No: " + (viewbooking.getVehicleNumber() != null ? viewbooking.getVehicleNumber() : "N/A"));
        holder.tvContactNo.setText("Contact No: " + (viewbooking.getContactNo() != null ? viewbooking.getContactNo() : "N/A"));
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }
}
