package com.example.carparkingapp;

public class Manage {
    public String name, id;
    public String city, timing;
    public  int price;
    public Manage(){

    }
    public Manage (String name, int price, String city, String timing) {
        this.name = name;
        this.price = price;
        this.city = city;
        this.timing = timing;
    }

    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }
    public String getCity() {
        return city;
    }
    public String getTiming() {
        return timing;
    }

}
