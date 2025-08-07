package com.example.carparkingapp;


public class ViewFeedback {
    public String userId;
    public String feedbackText;

    public ViewFeedback() {}

    public ViewFeedback(String userId, String feedbackText) {
        this.userId = userId;
        this.feedbackText = feedbackText;

    }

    public String getUserId() {
        return userId;
    }

    public String getFeedbackText() {
        return feedbackText;
    }


}


