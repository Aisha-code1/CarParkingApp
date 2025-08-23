package com.example.carparkingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageSlotActivity extends AppCompatActivity {

    RecyclerView rvManage;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_slot);

        rvManage = findViewById(R.id.rv_manageslot);
        add = findViewById(R.id.btn_add);

        rvManage.setLayoutManager(new LinearLayoutManager(this));

        add.setOnClickListener(v -> {
            Intent intent = new Intent(ManageSlotActivity.this, AddActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadManageData();
    }

    private void loadManageData() {
        FirebaseDatabase.getInstance().getReference("Manage")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Manage> manageList = new ArrayList<>();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Manage manage = ds.getValue(Manage.class);
                            if (manage != null) {
                                manage.setId(ds.getKey());
                                manageList.add(manage);
                            }
                        }

                        ManageAdapter adapter = new ManageAdapter(manageList, ManageSlotActivity.this, true, false,
                                new ManageAdapter.OnItemClickListener() {
                                    @Override
                                    public void onEdit(Manage manage) {
                                        Intent intent = new Intent(ManageSlotActivity.this, AddActivity.class);
                                        intent.putExtra("edit_mode", true);
                                        intent.putExtra("id", manage.getId());
                                        intent.putExtra("name", manage.getName());
                                        intent.putExtra("hourlyPrice", manage.getHourlyPrice());
                                        intent.putExtra("dailyPrice", manage.getDailyPrice());
                                        intent.putExtra("city", manage.getCity());
                                        intent.putExtra("timing", manage.getTiming());
                                        intent.putExtra("address", manage.getAddress());
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onDelete(Manage manage) {
                                        FirebaseDatabase.getInstance().getReference("Manage")
                                                .child(manage.getId())
                                                .removeValue()
                                                .addOnCompleteListener(task -> {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(ManageSlotActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                                        loadManageData(); // Refresh
                                                    } else {
                                                        Toast.makeText(ManageSlotActivity.this, "Failed to delete", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                });

                        rvManage.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ManageSlotActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
