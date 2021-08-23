package com.trinhtien2212.findroomrentalmobile.dao;

import com.trinhtien2212.findroomrentalmobile.retrofit.APIUtils;
import com.trinhtien2212.findroomrentalmobile.retrofit.DataClient;

import retrofit2.Callback;

public abstract class ConnectServer implements Callback{
    public static final int ADDROOM = 1;
    public static final int UPDATEROOM = 2;
    public static final int DELETEROOM = 3;
    public  static  final int DELETEUSER = 4;
    public static  final int GETALLUSER = 5;
    protected final String LINK_SERVER = "https://asia-southeast1-findroomrentalmobile.cloudfunctions.net/app/";
    protected DataClient dataClient = APIUtils.getData();
//    protected Retrofit retrofit2 = RetrofitClient.getClient(LINK_SERVER);
    public  abstract void connectServer(Object object,int action);

}
