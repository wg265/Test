package com.laioffer.washerdrymanagement.remote;

import com.laioffer.washerdrymanagement.remote.response.RemoteResponse;
import com.laioffer.washerdrymanagement.remote.response.UserInfo;
import com.laioffer.washerdrymanagement.ui.login.LoginEvent;
import com.laioffer.washerdrymanagement.ui.register.RegisterEvent;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("washerdrymanagement/login") // need to know exactly
    Call<RemoteResponse<UserInfo>> login(@Body LoginEvent body);

    @POST("washerdrymanagement/register") // need to know exactly
    Call<RemoteResponse<UserInfo>> register(@Body RegisterEvent body);

}
