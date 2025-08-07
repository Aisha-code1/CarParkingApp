package com.example.carparkingapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewFeedbackAdapter extends RecyclerView.Adapter<ViewFeedbackHolder> {

    private List<ViewFeedback> feedbackList;
    private Context context;

    public ViewFeedbackAdapter(List<ViewFeedback> feedbackList, Context context) {
        this.feedbackList = feedbackList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewFeedbackHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feedback_item, parent, false);
        return new ViewFeedbackHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewFeedbackHolder holder, int position) {
        ViewFeedback feedback = feedbackList.get(position);
        holder.tvFeedbackText.setText(feedback.getFeedbackText());
        holder.tvUserId.setText("User ID: " + feedback.getUserId());
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }
}
