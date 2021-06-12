package com.trinhtien2212.findhomerental.dao;

import com.trinhtien2212.findhomerental.model.Location;
import com.trinhtien2212.findhomerental.presenter.RoomPresenter;

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
        if(object instanceof Location) {
            Location location = (Location)object;
            if (action == ADDROOM) {
                this.action = ADDROOM;
                retrofit2.Call<String> callback = this.dataClient.insertData(location);
                callback.enqueue(this);
            } else if (action == UPDATEROOM) {
                this.action = UPDATEROOM;
                retrofit2.Call<String> callback = this.dataClient.updateData(location.getRoomID(), location);
                callback.enqueue(this);
            } else if (action == DELETEROOM) {
                this.action = DELETEROOM;
                retrofit2.Call<String> callback = this.dataClient.deleteData(location.getRoomID());
                callback.enqueue(this);
            }
        }

    }

    @Override
    public void onResponse(Call call, Response response) {
        if(action == ADDROOM || action == UPDATEROOM) {
            this.roomPresenter.saveImage();
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        roomPresenter.onFail();
    }
}
