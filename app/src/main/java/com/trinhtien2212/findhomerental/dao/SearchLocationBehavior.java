package com.trinhtien2212.findhomerental.dao;

import android.util.Log;

import com.google.gson.Gson;
import com.trinhtien2212.findhomerental.model.Location;
import com.trinhtien2212.findhomerental.presenter.SearchPresenter;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

public class SearchLocationBehavior extends ConnectServer {
    private SearchPresenter searchPresenter;
    public SearchLocationBehavior(SearchPresenter searchPresenter){
        this.searchPresenter = searchPresenter;
    }
    @Override
    public void connectServer(Object object, int action) {
        if(object instanceof Location) {
            Log.e("Location search",object.toString());
            Log.e("action",action+"");
            Location location = (Location)object;
            retrofit2.Call<Object> callback = this.dataClient.searchData(location.getAddress(), action);
            callback.enqueue(this);
        }
    }

    @Override
    public void onResponse(Call call, Response response) {
        Log.e("DA vo response","Da vo");
//        List<ResponseBody> locations = (List<SearchResultLocation>) response.body();
//        //ToDo
//        for(SearchResultLocation l: locations){
//            Log.e("Tag",l.getDistance()+"");
//        }
        if(response.isSuccessful()){
            Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );
            String json = new Gson().toJson(response.body());
            try {
                searchPresenter.parseJson(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
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
//        searchPresenter.onFail();
//        Log.e("Fai","Fail");
    }
}
