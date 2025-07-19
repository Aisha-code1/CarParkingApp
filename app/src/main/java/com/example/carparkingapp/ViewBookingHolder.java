package com.example.carparkingapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewBookingHolder extends RecyclerView.ViewHolder {

    TextView userId, tvMallName, tvVehicleType, tvVehicleNo, tvContactNo;



    public ViewBookingHolder(@NonNull View itemView) {
        super(itemView);
        userId = itemView.findViewById(R.id.tv_user);
        tvMallName = itemView.findViewById(R.id.tv_mallName);
        tvVehicleType = itemView.findViewById(R.id.vehicle_type);
        tvVehicleNo = itemView.findViewById(R.id.vehicle_no);




    }
}
