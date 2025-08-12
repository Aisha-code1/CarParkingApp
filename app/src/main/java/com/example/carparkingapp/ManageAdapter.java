package com.example.carparkingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ManageAdapter extends RecyclerView.Adapter<ManageViewHolder> {
   List<Manage> manageList;
    Context context;
    boolean isAdmin;
    public ManageAdapter(List<Manage> manageList, Context context, boolean isAdmin) {
        this.manageList = manageList;
        this.context = context;
        this.isAdmin = isAdmin;
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
        holder.tvName.setText(manage.name);
        holder.tvCity.setText(manage.city);

        if (isAdmin) {
            holder.daily_price.setVisibility(View.VISIBLE);
            holder.hourly_price.setVisibility(View.VISIBLE);
            holder.tvTiming.setVisibility(View.VISIBLE);
            holder.tvAddress.setVisibility(View.VISIBLE);
            holder.ivDelete.setVisibility(View.VISIBLE);
            holder.ivEdit.setVisibility(View.VISIBLE);
            holder.hourly_price.setText("Price: Rs " + manage.price);
            holder.daily_price.setText("Price: Rs " + manage.price);
            holder.tvTiming.setText("Timing: " + manage.timing);
            holder.tvAddress.setText("Address: " + manage.address);

            holder.ivDelete.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to delete?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    FirebaseDatabase.getInstance().getReference("Manage")
                            .child(manage.id)
                            .removeValue();
                    manageList.remove(manage);
                    notifyDataSetChanged();
                });
                builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                builder.setCancelable(false);
                builder.show();
            });

            holder.ivEdit.setOnClickListener(v -> {
                Intent intent = new Intent(context, AddActivity.class);
                intent.putExtra("edit_mode", true);
                intent.putExtra("id", manage.id);
                intent.putExtra("name", manage.name);
                intent.putExtra("price", manage.price);
                intent.putExtra("city", manage.city);
                intent.putExtra("timing", manage.timing);
                intent.putExtra("address", manage.address);
                context.startActivity(intent);
            });

        } else {
            holder.hourly_price.setVisibility(View.GONE);
            holder.daily_price.setVisibility(View.GONE);
            holder.tvTiming.setVisibility(View.GONE);
            holder.tvAddress.setVisibility(View.GONE);
            holder.ivDelete.setVisibility(View.GONE);
            holder.ivEdit.setVisibility(View.GONE);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("uuid", manage.id);
                intent.putExtra("name", manage.getName());
                intent.putExtra("price", manage.getPrice());
                intent.putExtra("city", manage.getCity());
                intent.putExtra("timing", manage.getTiming());
                intent.putExtra("address", manage.getAddress());
                context.startActivity(intent);
            });
        }
    }
    @Override
    public int getItemCount() {

        return this.manageList.size();
    }
}


