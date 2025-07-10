package com.example.carparkingapp;

public class Booking {
    public String id;
    public String userId;
    public String vehicleType;
    public String vehicleNumber;
    public String days;

    public Booking() {}

    public Booking(String id, String userId, String vehicleType, String vehicleNumber, String days) {
        this.id = id;
        this.userId = userId;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.days = days;
    }
}
