package com.trinhtien2212.mobilefindroomrental.retrofit;

public class APIUtils {
    public static final String BASE_URL = "https://asia-southeast1-mobilefindroomrental.cloudfunctions.net/app/";
    public static DataClient getData(){
        return RetrofitClient.getClient(BASE_URL).create(DataClient.class);
    }
}
