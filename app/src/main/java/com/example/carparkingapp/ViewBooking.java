package com.example.carparkingapp;


public class ViewBooking {
    public String id;
    public String userId;
    public String vehicleType;
    public String vehicleNumber;
    public String mallName;
    public String mallId;
    public String date;
    public String ContactNo; 

    public ViewBooking() { }

    public ViewBooking(String id, String userId, String vehicleType, String vehicleNumber,
                       String date, String mallName, String ContactNo) {
        this.id = id;
        this.userId = userId;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.date = date;
        this.mallName = mallName;
        this.ContactNo = ContactNo;
    }
}





