package com.example.carparkingapp;

public class Manage {
    private String id;
    private String name, city, timing, address;
    private int hourlyPrice, dailyPrice, capacity; // Capacity added

    public Manage(String name, int hourlyPrice, int dailyPrice, String city, String timing, String address) { }

    // Constructor updated to include capacity
    public Manage(String name, int hourlyPrice, int dailyPrice, String city, String timing, String address, int capacity) {
        this.name = name;
        this.hourlyPrice = hourlyPrice;
        this.dailyPrice = dailyPrice;
        this.city = city;
        this.timing = timing;
        this.address = address;
        this.capacity = capacity;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public int getHourlyPrice() { return hourlyPrice; }
    public int getDailyPrice() { return dailyPrice; }
    public String getCity() { return city; }
    public String getTiming() { return timing; }
    public String getAddress() { return address; }
    public int getCapacity() { return capacity; } // Getter for capacity


    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setHourlyPrice(int hourlyPrice) { this.hourlyPrice = hourlyPrice; }
    public void setDailyPrice(int dailyPrice) { this.dailyPrice = dailyPrice; }
    public void setCity(String city) { this.city = city; }
    public void setTiming(String timing) { this.timing = timing; }
    public void setAddress(String address) { this.address = address; }
    public void setCapacity(int capacity) { this.capacity = capacity; } // Setter for capacity
}
