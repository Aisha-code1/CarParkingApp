package com.example.carparkingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageSlotActivity extends AppCompatActivity {
RecyclerView rvManage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_manage_slot);
        Button add = findViewById(R.id.btn_add);
        rvManage = findViewById(R.id.rv_manageslot);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageSlotActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        FirebaseDatabase.getInstance().getReference("Manage")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        List<Manage> manageList = new ArrayList<>();
                        for (DataSnapshot manageSnapshot : snapshot.getChildren()) {
                           Manage manage = manageSnapshot.getValue(Manage.class);
                            if(manage != null) {
                                manage.id = manageSnapshot.getKey();
                                manageList.add(manage);
                            }
                        }
                      ManageAdapter adapter = new ManageAdapter(manageList, ManageSlotActivity.this);
                        rvManage.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ManageSlotActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
    }
}