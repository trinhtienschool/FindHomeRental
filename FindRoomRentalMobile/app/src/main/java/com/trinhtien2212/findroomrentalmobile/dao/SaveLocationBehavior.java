package com.trinhtien2212.findroomrentalmobile.dao;

import android.util.Log;

import com.trinhtien2212.findroomrentalmobile.model.Location;
import com.trinhtien2212.findroomrentalmobile.presenter.RoomPresenter;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Response;


public class SaveLocationBehavior extends ConnectServer {
    private RoomPresenter roomPresenter;
    private int action;
    public SaveLocationBehavior(RoomPresenter roomPresenter){
        this.roomPresenter = roomPresenter;

    }

    @Override
    public void connectServer(Object object,int action) {
        try {
            if (object instanceof Location) {
                Location location = (Location) object;
                String json_object = location.getJson();
                if (action == ADDROOM) {
                    this.action = ADDROOM;
                    retrofit2.Call<String> callback = this.dataClient.insertData(json_object);
                    callback.enqueue(this);
                } else if (action == UPDATEROOM) {
                    this.action = UPDATEROOM;
                    retrofit2.Call<String> callback = this.dataClient.updateData(location.getRoomID(), json_object);
                    callback.enqueue(this);
                } else if (action == DELETEROOM) {
                    this.action = DELETEROOM;
                    retrofit2.Call<String> callback = this.dataClient.deleteData(location.getRoomID());
                    callback.enqueue(this);
                }
            }
        }catch (JSONException e){

        }

    }

    @Override
    public void onResponse(Call call, Response response) {
        if(response.code() == 200) {
            Log.e("Addroom code 200","200");
            if (action == ADDROOM || action == UPDATEROOM) {
                this.roomPresenter.saveImage();
                Log.e("Dang vao onResponse", "On response");
            } else if (action == DELETEROOM) {
                this.roomPresenter.onSuccess();
            }
        }else{
            Log.e("Addroom code",response.code()+"");
            this.roomPresenter.onFail();
        }

    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Log.e("Error",t.toString());
        roomPresenter.onFail();
    }
}
