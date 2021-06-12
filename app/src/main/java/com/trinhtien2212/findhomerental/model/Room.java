package com.trinhtien2212.findhomerental.model;

public class Room {
    private String name,price, address;
    private int img;

    public Room(String name, String price, String address, int img) {
        this.name = name;
        this.price = price;
        this.address = address;
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
