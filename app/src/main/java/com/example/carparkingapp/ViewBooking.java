package com.example.carparkingapp;


public class ViewBooking {

    String userId;
    String mallName;
    String vehicleType;
    String vehicleNumber;
    String contactNo;
    String date;

    public ViewBooking(String bookingId, String mallName, String vehicleType, String vehicleNo, String contactNo) {

    }

    public ViewBooking(String userId, String mallName, String vehicleType, String vehicleNumber, String contactNo,String date) {
        this.userId = userId;
        this.mallName = mallName;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.contactNo = contactNo;
        this.date = date;
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
    public String getDate(){
        return date;
    }
}


