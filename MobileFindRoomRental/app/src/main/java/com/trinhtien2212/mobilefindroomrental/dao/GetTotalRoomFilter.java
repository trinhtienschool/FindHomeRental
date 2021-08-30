package com.trinhtien2212.mobilefindroomrental.dao;

import android.util.Log;

import com.google.gson.Gson;
import com.trinhtien2212.mobilefindroomrental.RoomListActivity;
import com.trinhtien2212.mobilefindroomrental.model.Location;
import com.trinhtien2212.mobilefindroomrental.presenter.RoomPresenter;
import com.trinhtien2212.mobilefindroomrental.presenter.SearchPresenter;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Response;

public class GetTotalRoomFilter extends ConnectServer{
    private RoomPresenter roomPresenter;
    public GetTotalRoomFilter(RoomPresenter roomPresenter){
        this.roomPresenter = roomPresenter;
    }
    @Override
    public void connectServer(Object object, int action) {
        String[] dates = (String[])object;
        String dateStart = dates[0];
        String dateEnd = dates[1];
        Log.e("Location search",object.toString());
        Log.e("action",action+"");

        retrofit2.Call<String> callback = this.dataClient.getTotalRoomFilter(dateStart,dateEnd);
        callback.enqueue(this);

    }

    @Override
    public void onResponse(Call call, Response response) {
        Log.e("DA vo response","Da vo");
//        //ToDo

        if(response.isSuccessful()){
            Log.e("Total",response.body().toString());
            roomPresenter.returnTotalRoom(response.body().toString());
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        roomPresenter.onFail();
        Log.e("Error",t.getMessage());
        Log.e("Error",t.toString());
//        HttpException exception = (HttpException) t;
//        switch (exception.code()) {
//            case 400:
//                // Handle code 400
//
//                Log.e("Code",400+"");
//                break;
//            case 500:
//                // Handle code 500
//                Log.e("Code",500+"");
//                break;
//            default:
//                break;
//        }

//        Log.e("Fai","Fail");
    }
}
