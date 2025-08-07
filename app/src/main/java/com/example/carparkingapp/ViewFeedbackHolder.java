

package com.example.carparkingapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewFeedbackHolder extends RecyclerView.ViewHolder {

    TextView tvFeedbackText, tvUserId;

    public ViewFeedbackHolder(@NonNull View itemView) {
        super(itemView);
        tvFeedbackText = itemView.findViewById(R.id.tv_feedback_text);
        tvUserId = itemView.findViewById(R.id.tv_user_id);
    }
}