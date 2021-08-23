package com.trinhtien2212.findroomrentalmobile.model;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Notification implements Serializable {
    private String address,message,userUid,roomId;
    private Date dateCreated;

    public Notification(String address, String message, String userUid, String roomId, Date dateCreated) {
        this.address = address;
        this.message = message;
        this.userUid = userUid;
        this.roomId = roomId;
        this.dateCreated = dateCreated;
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
    public void setNotification(DocumentSnapshot documentSnapshot){
        this.userUid = (String) documentSnapshot.get("userUid");
        this.address = (String) documentSnapshot.get("address");
        this.message = (String) documentSnapshot.get("message");
        this.roomId = (String)documentSnapshot.get("roomId");
//        Log.e("DAte",map.get("dateCreated").toString());
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        try {
//            this.dateCreated = simpleDateFormat.parse(map.get("dateCreated").toString());
//        }catch (ParseException parseException){
//            parseException.printStackTrace();
//        }
        this.dateCreated = documentSnapshot.getDate("dateCreated");
    }
    public Map<String,Object>convertToMap(){
        Map<String,Object>map = new HashMap<String,Object>();
        map.put("userUid",this.userUid);
        map.put("address",this.address);
        map.put("message",this.message);
        map.put("roomId",this.roomId);
        map.put("dateCreated",this.dateCreated);
        return map;

    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "address='" + address + '\'' +
                ", message='" + message + '\'' +
                ", userUid='" + userUid + '\'' +
                ", roomId='" + roomId + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
