package com.example.carparkingapp;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewBookingHolder extends RecyclerView.ViewHolder {

    TextView tvMallName, tvVehicleType, tvVehicleNo, tvDate, tvContactNo;

    public ViewBookingHolder(@NonNull View itemView) {
        super(itemView);
        tvMallName = itemView.findViewById(R.id.mall_name);
        tvVehicleType = itemView.findViewById(R.id.vehicle_type);
        tvVehicleNo = itemView.findViewById(R.id.vehicle_no);
        tvDate = itemView.findViewById(R.id.date);
        tvContactNo = itemView.findViewById(R.id.contact);
    }
}
