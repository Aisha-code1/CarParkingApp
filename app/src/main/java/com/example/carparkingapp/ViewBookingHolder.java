package com.example.carparkingapp;

import android.widget.TextView;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ViewBookingHolder extends RecyclerView.ViewHolder {
    TextView userId, tvVehicleType, tvContactNo,tvVehicleNo, tvDays,tvMallName;

    public ViewBookingHolder(View itemView) {
        super(itemView);
        userId = itemView.findViewById(R.id.tv_user);
        tvMallName = itemView.findViewById(R.id.tv_mallName);
        tvVehicleType = itemView.findViewById(R.id.vehicle_type);
        tvVehicleNo = itemView.findViewById(R.id.vehicle_no);
        tvContactNo = itemView.findViewById(R.id.tvContactNo);

    }
}
