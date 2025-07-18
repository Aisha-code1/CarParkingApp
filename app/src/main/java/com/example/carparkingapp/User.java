package com.example.carparkingapp;

public class User {
    public String name;
    public String email, role;
    private String imageId;
    public User() {}

    public User(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }
public String getImageId(){

        return imageId;
}
public void setImageId(String imageId){
        this.imageId = imageId;
        return;
}
}
