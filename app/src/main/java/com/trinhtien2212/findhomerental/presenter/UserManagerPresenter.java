package com.trinhtien2212.findhomerental.presenter;


import android.util.Log;

import com.trinhtien2212.findhomerental.dao.CheckAdmin;
import com.trinhtien2212.findhomerental.dao.ConnectServer;
import com.trinhtien2212.findhomerental.dao.GetAllUserBehavior;
import com.trinhtien2212.findhomerental.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserManagerPresenter implements StatusResult,IUserResult {
//    List<User>users;
    ConnectServer connectServer;
    IUserResult iUserResult;
    CheckAdmin checkAdmin;
    StatusResult statusResult;
    public UserManagerPresenter(IUserResult iUserResult,StatusResult statusResult){
        this.iUserResult = iUserResult;
        connectServer = new GetAllUserBehavior(this);
        checkAdmin = CheckAdmin.getInstance();
        this.statusResult = statusResult;
//        users = new ArrayList<User>();
    }
    public void getAllUsers(){
        connectServer.connectServer(null, ConnectServer.GETALLUSER);
    }
    public void deleteUser(User user){
        connectServer.connectServer(user, ConnectServer.DELETEUSER);

    }
    public void checkIsAdmin(String userUid){
        checkAdmin.checkAdmin(userUid,this);
    }
    public void parseJson(String json) throws JSONException {
        if(json.equalsIgnoreCase("{\"users\":[{}]}"))
            return;
        List<User>users = new ArrayList<User>();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("users");
        for(int i=0;i<jsonArray.length();i++){
            JSONObject user = jsonArray.getJSONObject(i);
            JSONObject userInfo = user.getJSONObject("user");

            User user_object = new User();
            user_object.setUser(userInfo);
            users.add(user_object);
        }
        for(User user :users){
            Log.e("user",user.toString()) ;
        }
        returnUser(users);
    }

    @Override
    public void onFail() {
        statusResult.onFail();
    }

    @Override
    public void onSuccess() {
        statusResult.onSuccess();
    }

    @Override
    public void returnUser(List<User> users) {
        iUserResult.returnUser(users);
    }
}
