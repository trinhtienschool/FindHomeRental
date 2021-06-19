package com.trinhtien2212.findhomerental.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Location{
    private String address;
    private double lat;
    private double lng;
    private boolean isDeleted;
    private String roomID;
    private double distance;
    public Location(String address,double lat, double lng, boolean isDeleted,String roomID) {
        this.lat = lat;
        this.lng = lng;
        this.isDeleted = isDeleted;
        this.roomID = roomID;
        this.address = address;
    }
    public Location(String address,boolean isDeleted,String roomID){
        this.address = address;
        this.roomID = roomID;
        this.isDeleted = isDeleted;
    }
    public Location(String address){
        this.address = address;
    }
    public Location() {
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    public void setLocation(JSONObject jsonObject) throws JSONException {
        this.roomID = jsonObject.getString("roomID");
        this.address = jsonObject.getString("address");
        this.lat = jsonObject.getDouble("lat");
        this.lng = jsonObject.getDouble("lng");
        this.isDeleted = jsonObject.getBoolean("isDeleted");
        this.distance = jsonObject.getDouble("distance");
    }
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String,Object> convertToMap() {
        Map<String,Object>map = new HashMap<String,Object>();
        map.put("roomId",this.roomID);
        map.put("lat",this.lat);
        map.put("lng",this.lng);
        map.put("isDeleted",this.isDeleted);
        return map;
    }

    @Override
    public String toString() {
        return "Location{" +
                "address='" + address + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", isDeleted=" + isDeleted +
                ", roomID='" + roomID + '\'' +
                ", distance=" + distance +
                '}';
    }
}
