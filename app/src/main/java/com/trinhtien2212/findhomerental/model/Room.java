package com.trinhtien2212.findhomerental.model;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Room implements Serializable {



    public Room(int cost, String address) {
        this.cost = cost;
        this.address = address;
    }

    private String roomID, userCreatedId, address, imageUrl, description;
    private Date dateCreated;
    public static boolean isDeleted, isPark, isWifi, isHotWater, isFre, isAttic, isTivi, isWardrobe, isFence, isWC, isFreeTime, isAirCondition;
    private float area;
    private String phone;
    private int cost, deposit, eleCost, watCost;
    private Location location;
    private List<String> images = new ArrayList<>();

    public Room() {
        dateCreated = new Date();
        Log.e("Contructor", "Dang vao contructor");
//       isDeleted = false;
//       isPark = false;
//       isWifi = false;
//       isHotWater = false;
//       isFre = false;
//       isAttic = false;
//       isTivi = false;
//       isWardrobe = false;
//       isFence = false;
//       isWC = false;
//       isFreeTime = false;
//       isAirCondition = false;
    }

    public Room(String userCreatedId, String address, String phone, String imageUrl, String description, Date dateCreated, boolean isDeleted, boolean isPark, boolean isWifi, boolean isHotWater, boolean isFre, boolean isAttic, boolean isTivi, boolean isWardrobe, boolean isFence, boolean isWC, boolean isFreeTime, float area, int cost, int deposit, int eleCost, int watCost, boolean isAirCondition) {
        this.userCreatedId = userCreatedId;
        this.address = address;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.description = description;
        this.dateCreated = dateCreated;
        this.isDeleted = isDeleted;
        this.isPark = isPark;
        this.isWifi = isWifi;
        this.isHotWater = isHotWater;
        this.isFre = isFre;
        this.isAttic = isAttic;
        this.isTivi = isTivi;
        this.isWardrobe = isWardrobe;
        this.isFence = isFence;
        this.isWC = isWC;
        this.isFreeTime = isFreeTime;
        this.area = area;
        this.cost = cost;
        this.deposit = deposit;
        this.eleCost = eleCost;
        this.watCost = watCost;
        this.isAirCondition = isAirCondition;
    }

    public Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userCreatedId", this.userCreatedId);
        map.put("dateCreated", this.dateCreated);
        map.put("isDeleted", this.isDeleted);
        map.put("address", this.address);
        map.put("phone", this.phone);
        map.put("imageUrl", this.imageUrl);
        map.put("description", this.description);
        map.put("area", this.area);
        map.put("cost", this.cost);
        map.put("deposit", this.deposit);
        map.put("eleCost", this.eleCost);
        map.put("watCost", this.watCost);
        map.put("isPark", this.isPark);
        map.put("isWifi", this.isWifi);
        map.put("isHotWater", this.isHotWater);
        map.put("isFre", this.isFre);
        map.put("isAttic", this.isAttic);
        map.put("isTivi", this.isTivi);
        map.put("isWardrobe", this.isWardrobe);
        map.put("isFence", this.isFence);
        map.put("isWC", this.isWC);
        map.put("isFreeTime", this.isFreeTime);
        map.put("isAirCondition", this.isAirCondition);
        return map;
    }

    public Map<String, String> convertImagesListToMap() {
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < this.images.size(); i++) {
            map.put(i + "", images.get(i));
        }
        return map;
    }

    //    public void generateRoom(String address){
//        String[] userIds = {"8aN1S6AkhQXRNNqAzMuY01mybmZ2",
//        "NBVp1vcmqBfabDVZ9mrx90RwaXj1",
//        "h1PykH8RBNbaYi18K6jfnfaNV2x1",
//        "mfSmbqjLoKd8YgphOJuZrQtJ7cj1",
//        "p1O3NUAmPpeGrlXkMtLmCpKjoSW2",
//        "xgXoEd4sViZN4vWQOoRsqW9lPwW2"};
//
//        this.setUserCreatedId(userIds[random(6)]);
//        this.setDateCreated(new Date());
//        this.setDeleted(false);
//        this.setAddress(address);
//        this.setPhone("0335364399");
//        this.setDescription("Phòng trọ tiện nghi, thoải mái, mọi chi tiết xin liên hệ rõ hơn qua điện thoại");
//        this.setArea(random(10)+11);
//        this.setCost(random(1500000)+1000000);
//        this.setDeposit(random(500000)+1000000);
//        this.setEleCost(random(2000)+3000);
//        this.setWatCost(random(5000)+5000);
//        this.setPark(randomTrueFalse());
//        this.setWifi(randomTrueFalse());
//        this.setHotWater(randomTrueFalse());
//        this.setFre(randomTrueFalse());
//        this.setAttic(randomTrueFalse());
//        this.setTivi(randomTrueFalse());
//        this.setWardrobe(randomTrueFalse());
//        this.setFence(randomTrueFalse());
//        this.setWC(randomTrueFalse());
//        this.setFreeTime(randomTrueFalse());
//    }
//    public String saveDB(){
//        RoomDB roomDB = RoomDB.getInstance();
//        return roomDB.addRoom(this);
//    }
//    public void saveImages(){
//        RoomDB roomDB = RoomDB.getInstance();
//
//    }
    public int random(int start, int end) {
        Random random = new Random();
        return random.nextInt(20) + 11;
    }

    public boolean randomTrueFalse() {
        Random random = new Random();
        return random.nextBoolean();
    }

    public int random(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }

    public String getUserCreatedId() {
        return userCreatedId;
    }

    public void setUserCreatedId(String userCreatedId) {
        this.userCreatedId = userCreatedId;
    }

    public String getAddress() {
        Log.e("Address", "Dang vao address");
        return address;

    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isPark() {
        return isPark;
    }

    public void setIsPark(boolean park) {
        isPark = park;
    }

    public boolean isWifi() {
        return isWifi;
    }

    public void setIsWifi(boolean wifi) {
        isWifi = wifi;
    }

    public boolean isHotWater() {
        return isHotWater;
    }

    public void setIsHotWater(boolean hotWater) {
        isHotWater = hotWater;
    }

    public boolean isFre() {
        return isFre;
    }

    public void setIsFre(boolean fre) {
        isFre = fre;
    }

    public boolean isAttic() {
        return isAttic;
    }

    public void setIsAttic(boolean attic) {
        isAttic = attic;
    }

    public boolean isTivi() {
        return isTivi;
    }

    public void setIsTivi(boolean tivi) {
        isTivi = tivi;
    }

    public boolean isWardrobe() {
        return isWardrobe;
    }

    public void setIsWardrobe(boolean wardrobe) {
        isWardrobe = wardrobe;
    }

    public boolean isFence() {
        return isFence;
    }

    public void setIsFence(boolean fence) {
        isFence = fence;
    }

    public boolean isWC() {
        return isWC;
    }

    public void setIsWC(boolean WC) {
        isWC = WC;
    }

    public boolean isFreeTime() {
        return isFreeTime;
    }

    public void setFreeTime(boolean freeTime) {
        isFreeTime = freeTime;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getEleCost() {
        return eleCost;
    }

    public void setEleCost(int eleCost) {
        this.eleCost = eleCost;
    }

    public int getWatCost() {
        return watCost;
    }

    public void setWatCost(int watCost) {
        this.watCost = watCost;
    }

    public void addImage(String image) {
        this.images.add(image);
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public List<String> getImages() {
        return images;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setImagesMap(Map<String, Object> images) {
        for (String key : images.keySet()) {
            this.images.add((String) images.get(key));
        }
    }

    public boolean isAirCondition() {
        return isAirCondition;
    }

    public void setIsAirCondition(boolean airCondition) {
        isAirCondition = airCondition;
    }

    @Override
    public String toString() {
        return "Room{" +
                "userCreatedId='" + userCreatedId + '\'' +
                ", address='" + address + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", dateCreated=" + dateCreated +
                ", isDeleted=" + isDeleted +
                ", isPark=" + isPark +
                ", isWifi=" + isWifi +
                ", isHotWater=" + isHotWater +
                ", isFre=" + isFre +
                ", isAttic=" + isAttic +
                ", isTivi=" + isTivi +
                ", isWardrobe=" + isWardrobe +
                ", isFence=" + isFence +
                ", isWC=" + isWC +
                ", isFreeTime=" + isFreeTime +
                ", area=" + area +
                ", phone='" + phone + '\'' +
                ", cost=" + cost +
                ", deposit=" + deposit +
                ", eleCost=" + eleCost +
                ", watCost=" + watCost +
                ", images=" + images +
                ", isAirCondition=" + isAirCondition +
                '}';
    }

    public void setUtilities(DocumentSnapshot document) {
        this.roomID = (String) document.getId();
        this.isDeleted = (boolean) document.get("isDeleted");
        this.isTivi = (boolean) document.get("isTivi");
        this.isWardrobe = (boolean) document.get("isWardrobe");
        this.isWifi = (boolean) document.get("isWifi");
        this.isFre = (boolean) document.get("isFre");
        if (document.get("isAirCondition") != null) {
            this.isAirCondition = (boolean) document.get("isAirCondition");
        }
        this.isAttic = (boolean) document.get("isAttic");
        this.isHotWater = (boolean) document.get("isHotWater");
        this.isWC = (boolean) document.get("isWC");
        this.isFreeTime = (boolean) document.get("isFreeTime");
        this.isFence = (boolean) document.get("isFence");
        this.isPark = (boolean) document.get("isPark");
    }
}
