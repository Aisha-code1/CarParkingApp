package com.example.carparkingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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
    public ManageAdapter(List<Manage> categoryList, Context context) {
        this.manageList = categoryList;
        this.context = context;
    }


    @NonNull
    @Override
    public ManageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.data_item, parent, false);
        return new ManageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageViewHolder holder, int position) {
        Manage category = manageList.get(position);
        holder.tvName.setText(category.name);
        holder.tvPrice.setText(category.price);
        holder.tvCity.setText(category.city);
        holder.tvTiming.setText(category.timing);
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(context, "You clicked Yes", Toast.LENGTH_SHORT).show();
                        FirebaseDatabase.getInstance().getReference("Categories")
                                .child(category.id)
                                .removeValue();
                        manageList.remove(category);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.manageList.size();
    }
}


