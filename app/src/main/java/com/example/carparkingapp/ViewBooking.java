package com.example.carparkingapp;


public class ViewBooking {

    String userId;
    String mallName;
    String vehicleType;
    String vehicleNumber;
    String contactNo;

    public ViewBooking() {

    }

    public ViewBooking(String userId, String mallName, String vehicleType, String vehicleNumber, String contactNo) {
        this.userId = userId;
        this.mallName = mallName;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.contactNo = contactNo;
    }

    public String getUserId() {
        return userId;
    }

    public String getMallName() {
        return mallName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getContactNo() {
        return contactNo;
    }
}


