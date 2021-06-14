package com.trinhtien2212.findhomerental.retrofit;

public class APIUtils {
    public static final String BASE_URL = "https://us-central1-data-ceremony-314315.cloudfunctions.net/app/";
    public static DataClient getData(){
        return RetrofitClient.getClient(BASE_URL).create(DataClient.class);
    }
}
