package com.example.carparkingapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ManageViewHolder extends RecyclerView.ViewHolder {

    public TextView tvName, hourly_price, daily_price, tvCity, tvTiming,tvAddress;
    ImageView ivDelete , ivEdit;
    public ManageViewHolder(@NonNull View dataitem) {
        super(dataitem);

        tvName = dataitem.findViewById(R.id.tv_name);
        hourly_price = dataitem.findViewById(R.id.hourly_price);
        daily_price = dataitem.findViewById(R.id.daily_price);
        tvCity = dataitem.findViewById(R.id.tv_city);
        tvTiming = dataitem.findViewById(R.id.tv_timing);
        tvAddress = dataitem.findViewById(R.id.tv_address);
        ivDelete = dataitem.findViewById(R.id.iv_delete);
        ivEdit  =  dataitem.findViewById(R.id.iv_edit);
    }
}
