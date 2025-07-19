package com.example.carparkingapp;


public class ViewBooking {
    public String id;
   public String userId;
   public String mallName;
    public String vehicleType;
    public String vehicleNumber;



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


