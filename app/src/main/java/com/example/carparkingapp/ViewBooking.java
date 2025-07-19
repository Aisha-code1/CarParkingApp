package com.example.carparkingapp;


public class ViewBooking {
    private String id;
    private String userId;
    private String mallName;
    private String vehicleType;
    private String vehicleNumber;
    private String contactNo;
    String userId;
    String mallName;
    String vehicleType;
    String vehicleNumber;


    public ViewBooking(String id, String bookingId, String mallName, String vehicleType, String vehicleNo) {

    }



    public ViewBooking(String userId, String mallName, String vehicleType, String vehicleNumber) {
        this.userId = userId;
        this.mallName = mallName;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;

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


}


