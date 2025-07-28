package com.example.carparkingapp;

public class Feedback {
    public String userId;
    public String feedbackText;

    public Feedback(){

    }
    public Feedback(String userId, String feedbackText) {
        this.userId = userId;
        this.feedbackText = feedbackText;
    }
}
