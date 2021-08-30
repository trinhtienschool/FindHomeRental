package com.trinhtien2212.mobilefindroomrental.dao;

import android.util.Log;

import com.google.gson.Gson;
import com.trinhtien2212.mobilefindroomrental.model.User;
import com.trinhtien2212.mobilefindroomrental.presenter.UserManagerPresenter;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Response;

public class GetAllUserBehavior extends ConnectServer {
    int action;
    UserManagerPresenter userManagerPresenter;
    public GetAllUserBehavior(UserManagerPresenter userManagerPresenter){
        this.userManagerPresenter = userManagerPresenter;
    }


    @Override
    public void connectServer(Object object, int action) {
        this.action = action;
            if(action == GETALLUSER) {

                retrofit2.Call<Object> callback = this.dataClient.getAllUsers();
                callback.enqueue(this);
            }else if(action == DELETEUSER){
                if(object instanceof User) {
                    User user = (User)object;
                    retrofit2.Call<String> callback = this.dataClient.deleteUser(user.getUserUid());
                    callback.enqueue(this);
                }
            }

    }

    @Override
    public void onResponse(Call call, Response response) {
        if(this.action == GETALLUSER) {
            if (response.isSuccessful()) {
                Log.e("TAG", "response 33: " + new Gson().toJson(response.body()));
                String json = new Gson().toJson(response.body());
                try {
                    userManagerPresenter.parseJson(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else if(this.action == DELETEUSER){
            if(response.isSuccessful()){
                userManagerPresenter.onSuccess();
            }
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        userManagerPresenter.onFail();
    }
}
