package com.example.carparkingapp;

public class ViewBooking {
    private String id;
    private String userId;
    private String mallName;
    private String vehicleType;
    private String vehicleNumber;
    private String contactNo;
    private String date;

    // Required empty constructor for Firebase
    public ViewBooking() {
    }

    // Constructor with all fields
    public ViewBooking(String id, String userId, String mallName, String vehicleType,
                       String vehicleNumber, String contactNo, String date) {
        this.id = id;
        this.userId = userId;
        this.mallName = mallName;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.contactNo = contactNo;
        this.date = date;
    }

    // Getters
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

    public String getDate() {
        return date;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public void setDate(String date) {
        this.date = date;
    }
}






