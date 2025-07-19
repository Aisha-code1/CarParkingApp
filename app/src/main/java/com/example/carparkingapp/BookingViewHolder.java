package com.example.carparkingapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookingViewHolder extends RecyclerView.ViewHolder {
    TextView tvMallName, tvVehicleType, tvVehicleNumber, tvdate,  tvContactNo;

    public BookingViewHolder(@NonNull View itemView) {
        super(itemView);
        tvVehicleType = itemView.findViewById(R.id.vehicle_type);
        tvVehicleNumber = itemView.findViewById(R.id.vehicle_no);
        tvdate = itemView.findViewById(R.id.date);
        tvMallName = itemView.findViewById(R.id.mall_name);
        tvContactNo = itemView.findViewById(R.id.contact);
    }
}

