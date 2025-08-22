package com.example.carparkingapp;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewBookingHolder extends RecyclerView.ViewHolder {

    TextView tvMallName, tvVehicleType, tvVehicleNo, tvDate, tvContactNo, tvBookingType, tvStatus;
    Button btnConfirm;

    public ViewBookingHolder(@NonNull View itemView) {
        super(itemView);
        tvMallName = itemView.findViewById(R.id.mall_name);
        tvVehicleType = itemView.findViewById(R.id.vehicle_type);
        tvVehicleNo = itemView.findViewById(R.id.vehicle_no);
        tvDate = itemView.findViewById(R.id.date);
        tvContactNo = itemView.findViewById(R.id.contact);
        tvBookingType = itemView.findViewById(R.id.booking_type);
        tvStatus = itemView.findViewById(R.id.booking_status);
        btnConfirm = itemView.findViewById(R.id.btn_confirm);
    }
}
