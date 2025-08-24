package com.example.carparkingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ManageAdapter extends RecyclerView.Adapter<ManageAdapter.ManageViewHolder> {

    private List<Manage> manageList;
    private Context context;
    private boolean isAdmin;
    private boolean isCompact;

    public interface OnItemClickListener {
        void onEdit(Manage manage);
        void onDelete(Manage manage);
    }

    private OnItemClickListener listener;

    public ManageAdapter(List<Manage> manageList, Context context, boolean isAdmin, boolean isCompact, OnItemClickListener listener) {
        this.manageList = manageList;
        this.context = context;
        this.isAdmin = isAdmin;
        this.isCompact = isCompact;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ManageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.data_item, parent, false);
        return new ManageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageViewHolder holder, int position) {
        Manage manage = manageList.get(position);
        holder.tvName.setText(manage.getName());

        if (isCompact) {

            holder.tvCity.setText("City: " + manage.getCity());
            holder.hourly_price.setText("Hourly Price: " + manage.getHourlyPrice() + " Rs");
            
            holder.tvTiming.setVisibility(View.GONE);
            holder.tvAddress.setVisibility(View.GONE);
            holder.daily_price.setVisibility(View.GONE);

        } else {
            holder.tvCity.setText("City: " + manage.getCity());
            holder.tvTiming.setText("Timing: " + manage.getTiming());
            holder.tvAddress.setText("Address: " + manage.getAddress());
            holder.hourly_price.setText("Hourly Price: " + manage.getHourlyPrice() + " Rs");
            holder.daily_price.setText("Daily Price: " + manage.getDailyPrice() + " Rs");
        }
        if (isAdmin) {

            holder.ivEdit.setVisibility(View.VISIBLE);
            holder.ivDelete.setVisibility(View.VISIBLE);

            holder.ivEdit.setOnClickListener(v -> {
                if (listener != null) listener.onEdit(manage);
            });

            holder.ivDelete.setOnClickListener(v -> {
                if (listener != null) listener.onDelete(manage);
            });

        } else {

            holder.ivEdit.setVisibility(View.GONE);
            holder.ivDelete.setVisibility(View.GONE);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id", manage.getId());
                intent.putExtra("mallName", manage.getName());
                intent.putExtra("city", manage.getCity());
                intent.putExtra("timing", manage.getTiming());
                intent.putExtra("address", manage.getAddress());
                intent.putExtra("hourlyPrice", manage.getHourlyPrice());
                intent.putExtra("dailyPrice", manage.getDailyPrice());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return manageList.size();
    }

    public static class ManageViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, hourly_price, daily_price, tvCity, tvTiming, tvAddress;
        ImageView ivDelete, ivEdit;

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
}


