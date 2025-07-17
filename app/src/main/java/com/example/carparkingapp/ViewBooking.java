package com.example.carparkingapp;

public class ViewBooking {
    public String id;
    public String userId;
    public String vehicleType;
    public String vehicleNumber;
    public String days;
    public String mallName;

    public ViewBooking() {}

    public ViewBooking(String id, String userId, String vehicleType, String vehicleNumber, String days, String mallName) {
        this.id = id;
        this.userId = userId;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.days = days;
        this.mallName = mallName;
    }
    public String getId() {

        return id;
    }

    public String getuserId() {

        return userId;
    }

    public String getVehicleType() {

        return vehicleType;
    }

    public String getVehicleNumber() {

        return vehicleNumber;
    }

    public String getDays() {

        return days;
    }

        public String getmallName() {
        return mallName;
    }
}