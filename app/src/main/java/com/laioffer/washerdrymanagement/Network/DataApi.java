package com.laioffer.washerdrymanagement.Network;

import com.laioffer.washerdrymanagement.database.BackGround;
import com.laioffer.washerdrymanagement.database.Reservation;
import com.laioffer.washerdrymanagement.database.User;
import com.laioffer.washerdrymanagement.database.Item;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataApi {
    @POST("login")
    Call<ResponseBody> login(@Body RequestBody info);
    @GET("login")
    Call<User> login();
    @GET("logout")
    Call<ResponseBody> logout();
    @POST("register")
    Call<ResponseBody> register(@Body RequestBody info);
    @GET("getAllMachines")
    Call<List<Item>> getwasher();
    @POST("editfile")
    Call<ResponseBody> editfile(@Body RequestBody info);
    @GET("background")
    Call<BackGround> getbackground();
    @GET("getAllMachines")
    Call<List<Reservation>> getReservations();
    @POST("changeMachineStatus")
    Call<ResponseBody> deleteReservation(@Body RequestBody info);
}
