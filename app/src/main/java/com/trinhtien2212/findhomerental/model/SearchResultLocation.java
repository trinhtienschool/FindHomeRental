package com.trinhtien2212.findhomerental.model;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchResultLocation {
    private Location location;
    private double distance;

    public SearchResultLocation(Location location, double distance) {
        this.location = location;
        this.distance = distance;
    }

    public SearchResultLocation() {
    }
    public void parse(JSONObject jsonObject) throws JSONException {
        String roomID = jsonObject.getString("roomID");
        String address = jsonObject.getString("address");
        double lat = jsonObject.getDouble("lat");
        double lng = jsonObject.getDouble("lng");
        boolean isDeleted = jsonObject.getBoolean("isDeleted");
        this.distance = jsonObject.getDouble("distance");

        Location location = new Location(address,lat,lng,isDeleted,roomID);
        this.location = location;

    }
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
