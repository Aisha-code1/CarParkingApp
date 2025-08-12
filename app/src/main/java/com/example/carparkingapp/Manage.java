package com.example.carparkingapp;

public class Manage {
    public String name, id;
    public String city, timing,address;

    public  int price, rs;
       public Manage (String name, int price, int rs, String city, String timing,String address) {
        this.name = name;
        this.price = price;
        this.city = city;
        this.timing = timing;
        this.address = address;

    }

    public String getName() {

        return name;
    }
    public int getPrice() {

        return price;
    }
    public int getRs() {

        return rs;
    }
    public String getCity() {

        return city;
    }
    public String getTiming() {

        return timing;
    }
    public String getAddress(){

        return  address;
    }

}
