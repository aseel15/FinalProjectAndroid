package com.example.finalproject.model;

public class ReservedRoom {
    int id;
    int roomID;
    int userId;
    String check_In;
    String check_Out;
    int totalPrice;

    public ReservedRoom(int id, int roomID, int userId, String check_In, String check_Out, int totalPrice) {
        this.id = id;
        this.roomID = roomID;
        this.userId = userId;
        this.check_In = check_In;
        this.check_Out = check_Out;
        this.totalPrice = totalPrice;
    }

    public ReservedRoom(int roomID, String check_In, String check_Out){
        this.roomID=roomID;
        this.check_In=check_In;
        this.check_Out=check_Out;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCheck_In() {
        return check_In;
    }

    public void setCheck_In(String check_In) {
        this.check_In = check_In;
    }

    public String getCheck_Out() {
        return check_Out;
    }

    public void setCheck_Out(String check_Out) {
        this.check_Out = check_Out;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
