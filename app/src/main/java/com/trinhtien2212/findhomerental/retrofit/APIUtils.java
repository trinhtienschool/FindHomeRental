package com.trinhtien2212.findhomerental.retrofit;

public class APIUtils {
    public static final String BASE_URL = "http://192.168.1.5:5001/data-ceremony-314315/us-central1/app/";
    public static DataClient getData(){
        return RetrofitClient.getClient(BASE_URL).create(DataClient.class);
    }
}
