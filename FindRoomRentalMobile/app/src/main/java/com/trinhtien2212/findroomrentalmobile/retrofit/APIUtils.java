package com.trinhtien2212.findroomrentalmobile.retrofit;

public class APIUtils {
    public static final String BASE_URL = "https://asia-southeast1-findroomrentalmobile.cloudfunctions.net/app/";
    public static DataClient getData(){
        return RetrofitClient.getClient(BASE_URL).create(DataClient.class);
    }
}
