package com.example.carparkingapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ManageViewHolder extends RecyclerView.ViewHolder {

    public TextView tvName, hourly_price, daily_price, tvCity, tvTiming, tvAddress;
    public ImageView ivDelete, ivEdit;

    public ManageViewHolder(@NonNull View itemView) {
        super(itemView);

        tvName = itemView.findViewById(R.id.tv_name);
        hourly_price = itemView.findViewById(R.id.hourly_price);
        daily_price = itemView.findViewById(R.id.daily_price);
        tvCity = itemView.findViewById(R.id.tv_city);
        tvTiming = itemView.findViewById(R.id.tv_timing);
        tvAddress = itemView.findViewById(R.id.tv_address);
        ivDelete = itemView.findViewById(R.id.iv_delete);
        ivEdit = itemView.findViewById(R.id.iv_edit);
    }
}
