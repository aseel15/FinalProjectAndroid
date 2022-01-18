package com.example.finalproject.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable {
    private int id;
    private String roomType;
    private int price;
    private String bedType;
    private int numOfBeds;
    private String roomSize;
    private String imageURL;
    ArrayList<Room>roomArrayList=new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int roomId) {
        this.id = id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }



    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public int getNumOfBeds() {
        return numOfBeds;
    }

    public void setNumOfBeds(int numOfBeds) {
        this.numOfBeds = numOfBeds;
    }


    public String getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(String roomSize) {
        this.roomSize = roomSize;
    }



    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }



    public Room(int roomNumber, String roomType, int price, String bedType, int numOfBeds, String roomSize, String imageURL) {
        this.id = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.bedType = bedType;
        this.numOfBeds = numOfBeds;
        this.roomSize = roomSize;
        this.imageURL= imageURL;
    }
    public Room(String roomType, int price, String imgId){
        this.roomType=roomType;
        this.price=price;
        this.imageURL=imgId;

    }
    /*public Room getRoomObject(int objectNumber){
        for(int i=0;i<roomArrayList.size();i++)
            if(objectNumber==roomArrayList.get(i).getId())
                return roomArrayList.get(i);
        return null;
    }*/



}
