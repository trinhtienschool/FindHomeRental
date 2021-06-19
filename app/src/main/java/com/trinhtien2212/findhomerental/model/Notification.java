package com.trinhtien2212.findhomerental.model;

import java.util.HashMap;
import java.util.Map;

public class Notification {
    private String address,message,userUid;

    public Notification(String address, String message, String userUid) {
        this.address = address;
        this.message = message;
        this.userUid = userUid;
    }

    public Notification() {
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public void setNotification(Map<String,Object>map){
        this.userUid = (String) map.get("userUid");
        this.address = (String) map.get("address");
        this.message = (String) map.get("message");
    }
    public Map<String,Object>convertToMap(){
        Map<String,Object>map = new HashMap<String,Object>();
        map.put("userUid",this.userUid);
        map.put("address",this.address);
        map.put("message",this.message);
        return map;

    }

    @Override
    public String toString() {
        return "Notification{" +
                "address='" + address + '\'' +
                ", message='" + message + '\'' +
                ", userUid='" + userUid + '\'' +
                '}';
    }
}
