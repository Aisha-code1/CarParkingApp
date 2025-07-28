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
        ViewBooking booking = bookingList.get(position);

        holder.tvMallName.setText("Mall: " + booking.mallName);
        holder.tvVehicleType.setText("Vehicle Type: " + booking.vehicleType);
        holder.tvVehicleNo.setText("Vehicle No: " + booking.vehicleNumber);
        holder.tvDate.setText("Date: " + booking.date);
        holder.tvContactNo.setText("Contact No: " + booking.ContactNo);
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }
}
