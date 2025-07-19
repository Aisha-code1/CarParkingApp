package com.example.carparkingapp;



public class ViewBooking {
    private String id;
    private String userId;
    private String mallName;
    private String vehicleType;
    private String vehicleNumber;
    private String contactNo;

    public ViewBooking() {

    }

    public ViewBooking(String id, String userId, String mallName, String vehicleType, String vehicleNumber, String contactNo) {
        this.id = id;
        this.userId = userId;
        this.mallName = mallName;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.contactNo = contactNo;
    }


    public String getId() {
        return id;
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


