package com.example.finalproject.model;

public class ServiceItem {
    private String nameService;
    private int imgService;

    public ServiceItem(String nameService, int imgService) {
        this.nameService = nameService;
        this.imgService = imgService;
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public int getImgService() {
        return imgService;
    }

    public void setImgService(int imgService) {
        this.imgService = imgService;
    }
}
