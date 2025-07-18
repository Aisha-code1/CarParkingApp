package com.example.carparkingapp;


public class ViewBooking {

    String userId;
    String mallName;
    String vehicleType;
    String vehicleNo;
    String contactNo;


    public ViewBooking() {
    }

    public ViewBooking(String userId, String mallName, String vehicleType, String vehicleNo, String contactNo) {
        this.userId = userId;
        this.mallName = mallName;
        this.vehicleType = vehicleType;
        this.vehicleNo = vehicleNo;
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

    public String getVehicleNo() {
        return vehicleNo;
    }

    public String getContactNo() {
        return contactNo;
    }


}