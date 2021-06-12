package com.trinhtien2212.findhomerental.retrofit;

import com.trinhtien2212.findhomerental.model.Location;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface DataClient {

//    @Headers("Content-Type: application/json")
    @POST("api/add-location/")
    Call<String> insertData(@Body Location location);
    @PUT("api/update-location")
    Call<String> updateData(@Query("roomID") String roomID, @Body Location location);
    @DELETE("api/delete-location")
    Call<String> deleteData(@Query("roomID") String roomID);
    @GET("api/search-room")
    Call<Object> searchData(@Query("address") String roomID,
                                             @Query("distance") int distance);
    //user
    @GET("api/list-all-users")
    Call<Object> getAllUsers();

    @DELETE("api/delete-user")
    Call<String> deleteUser(@Query("userUid") String userUid);
}
