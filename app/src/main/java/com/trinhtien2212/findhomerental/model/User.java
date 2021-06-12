package com.trinhtien2212.findhomerental.model;

<<<<<<< HEAD
public class User {
    private String name, email;
    private int img;

    public User(String name, String email, int img) {
        this.name = name;
        this.email = email;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
=======
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
>>>>>>> f26b338d4693b0f5733aa2ff69a394a8f79e82b4
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

<<<<<<< HEAD
    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
=======
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
>>>>>>> f26b338d4693b0f5733aa2ff69a394a8f79e82b4
    }
}
