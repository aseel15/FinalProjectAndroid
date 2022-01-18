package com.example.finalproject.model;

public class ServiceFromTable {
    int id;
    int userId;
    int roomId;
    String serviceName;
    int totalPrice;

    public ServiceFromTable(int id, int userId, int roomId, String serviceName, int totalPrice) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.serviceName = serviceName;
        this.totalPrice = totalPrice;
    }

    public ServiceFromTable(int id, int roomId, int userId, int totalPrice) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.totalPrice = totalPrice;
    }

    public ServiceFromTable(int roomId,int userId, int totalPrice) {
        this.userId = userId;
        this.roomId = roomId;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
