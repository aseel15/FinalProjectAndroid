package com.example.finalproject;

public class Trip {
     private int id;
     private String name;
     private String description;
     private String Date;
     private int price;

    public Trip(int id, String name, String description, String date, int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        Date = date;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
