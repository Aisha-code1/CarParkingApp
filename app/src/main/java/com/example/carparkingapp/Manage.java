package com.example.carparkingapp;

public class Manage {
    public String id;
    public String name, city, timing, address;
    public int hourlyPrice, dailyPrice;

    public Manage() {

    }

    public Manage(String name, int hourlyPrice, int dailyPrice, String city, String timing, String address) {
        this.name = name;
        this.hourlyPrice = hourlyPrice;
        this.dailyPrice = dailyPrice;
        this.city = city;
        this.timing = timing;
        this.address = address;
    }


    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getHourlyPrice() {
        return hourlyPrice;
    }
    public int getDailyPrice() {
        return dailyPrice;
    }
    public String getCity() {
        return city;
    }
    public String getTiming() {
        return timing;
    }
    public String getAddress() {
        return address;
    }


    public void setId(String id) {
        this.id = id;
    }
    public void setHourlyPrice(int hourlyPrice) {
        this.hourlyPrice = hourlyPrice;
    }
    public void setDailyPrice(int dailyPrice) {
        this.dailyPrice = dailyPrice;
    }
}
