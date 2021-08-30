package com.trinhtien2212.mobilefindroomrental.model;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String userUid;
    private String displayName;
    private String email;
    private String photoUrl;

    public User(String userUid, String displayName, String email, String photoUrl) {
        this.userUid = userUid;
        this.displayName = displayName;
        this.email = email;
        this.photoUrl = photoUrl;
    }
    public User(String displayName,String photoUrl,String userUid){
        this.userUid = userUid;
        this.photoUrl=photoUrl;
        this.displayName=displayName;
    }
    public User() {
    }

    public User(String userUid) {
        this.userUid = userUid;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "userUid='" + userUid + '\'' +
                ", displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                '}';

    }

    public void setUser(JSONObject userInfo) throws JSONException {
        this.userUid = userInfo.getString("userUid");
        this.displayName = userInfo.getString("displayName");
        this.email = userInfo.getString("email");
        this.photoUrl = userInfo.getString("photoUrl");
    }
}
