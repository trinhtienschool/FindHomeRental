package com.trinhtien2212.findroomrentalmobile.presenter;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.trinhtien2212.findroomrentalmobile.dao.RoomDB;
import com.trinhtien2212.findroomrentalmobile.dao.UserDB;
import com.trinhtien2212.findroomrentalmobile.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminUserPresenter implements StatusResult{
    private static Map<String,Object>userAdminIdMap;
    private static volatile AdminUserPresenter instance;
    private UserDB userDB;
    private StatusResult statusResult;
    public AdminUserPresenter(){
        userDB = UserDB.getInstance();
        userDB.getAllUserAdminIds(this);

    }
    public static synchronized AdminUserPresenter getInstance() {
        if (instance == null) {
            instance = new AdminUserPresenter();

        }
        return instance;
    }
    public void addUserAdmin(String userId,StatusResult statusResult){
        userAdminIdMap.put(getNextIndex(),userId);
        this.statusResult = statusResult;
        userDB.updateUserAdmin(userAdminIdMap,this);
    }
    public void delUserAdmin(String userId,StatusResult statusResult){
        for(String key: userAdminIdMap.keySet()){
            if(userAdminIdMap.get(key).equals(userId)){
                userAdminIdMap.remove(key);
                Map<String,Object>map = new HashMap<>();
                map.put(key,FieldValue.delete());
                userDB.updateUserAdmin(map,this);
                this.statusResult = statusResult;
                return;
            }
        }
        statusResult.onFail();

    }
    private String getNextIndex() {
        int max = 0;
        for (String key : userAdminIdMap.keySet()) {
            int key_num = Integer.parseInt(key);
            max = Math.max(max, key_num);
        }
        return String.valueOf(max + 1);
    }
    public void returnUserAdminList(DocumentSnapshot documentSnapshot){
        userAdminIdMap = documentSnapshot.getData();
        Log.e("MapUserAdmin",userAdminIdMap.toString());
    }

    public boolean checkIsAdmin(String userId){
        if(userAdminIdMap !=null){
            Log.e("AdminUserPresenter","!null");
            return checkAdmin(userId);
        }else{
            Log.e("AdminUserPresenter","null");
            return false;
        }
    }
    private boolean checkAdmin(String userId){
        for(String key: userAdminIdMap.keySet()){
            if(userAdminIdMap.get(key).equals(userId)) {

                return true;
            }
        }
        Log.e("AdminUserPresenter","Khong co cai nao");
        return false;
    }

    @Override
    public void onFail() {
        statusResult.onSuccess();
    }

    @Override
    public void onSuccess() {
        statusResult.onSuccess();
    }
}
