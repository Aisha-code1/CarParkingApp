package com.example.carparkingapp;

public class Booking {
    public String id;
    public String userId;
    public String vehicleType;
    public String vehicleNumber;
    public String mallName;
    public String mallId;

    public Booking() {}

    public Booking(String id, String userId, String vehicleType, String vehicleNumber,  String mallName) {
        this.id = id;
        this.userId = userId;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.mallName = mallName;
    }
}
