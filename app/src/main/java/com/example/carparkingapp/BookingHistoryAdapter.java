package com.example.carparkingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingViewHolder> {
    List<Booking> bookinglist;
   Context context;

   public BookingHistoryAdapter(List<Booking> bookinglist, Context context){
   this.bookinglist = bookinglist;
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
        Booking booking = bookinglist.get(position);
        holder.tvVehicleType.setText("Type: " + booking.vehicleType);
        holder.tvVehicleNumber.setText("Number: " + booking.vehicleNumber);
        holder.tvDays.setText("Days: " + booking.days);
    }

    @Override
    public int getItemCount() {
        return bookinglist.size();
    }
}