package com.example.carparkingapp;

import android.os.Bundle;
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

public class ViewSlot extends AppCompatActivity {

    RecyclerView rvView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_slot);

        rvView = findViewById(R.id.rv_viewslot);
        rvView.setLayoutManager(new LinearLayoutManager(this));

        loadManageData();
    }

    private void loadManageData() {
        FirebaseDatabase.getInstance().getReference("Manage")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Manage> manageList = new ArrayList<>();
                        for (DataSnapshot manageSnapshot : snapshot.getChildren()) {
                            Manage manage = manageSnapshot.getValue(Manage.class);
                            if (manage != null) {
                                manage.setId(manageSnapshot.getKey());
                                manageList.add(manage);
                            }
                        }


                        ManageAdapter adapter = new ManageAdapter(manageList, ViewSlot.this, false, null);
                        rvView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ViewSlot.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

