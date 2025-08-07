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

public class ViewFeedbackActivity extends AppCompatActivity {

    private RecyclerView rvFeedback;
    private ViewFeedbackAdapter adapter;
    private List<ViewFeedback> feedbackList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);

        rvFeedback = findViewById(R.id.rv_feedback);
        rvFeedback.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ViewFeedbackAdapter(feedbackList, this);
        rvFeedback.setAdapter(adapter);

        loadFeedback();
    }

    private void loadFeedback() {
        FirebaseDatabase.getInstance().getReference("Feedbacks")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        feedbackList.clear();
                        for (DataSnapshot feedbackSnap : snapshot.getChildren()) {
                            ViewFeedback feedback = feedbackSnap.getValue(ViewFeedback.class);
                            if (feedback != null) {
                                feedbackList.add(feedback);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ViewFeedbackActivity.this, "Failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}