package com.example.carparkingapp;

import android.widget.TextView;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ViewBookingHolder extends RecyclerView.ViewHolder {
    TextView tvUserId, tvVehicleType, tvVehicleNo, tvDays;

    public ViewBookingHolder(View itemView) {
        super(itemView);
        tvUserId = itemView.findViewById(R.id.tv_user);
        tvVehicleType = itemView.findViewById(R.id.tv_vehicle_type);
        tvVehicleNo = itemView.findViewById(R.id.tv_vehicle_no);
        tvDays = itemView.findViewById(R.id.tv_days);
    }
}
