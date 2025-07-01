package com.example.coffeeshopmanagementandroid.data.api;


import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.auth.request.LoginRequest;
import com.example.coffeeshopmanagementandroid.data.dto.auth.response.LoginResponse;
import com.example.coffeeshopmanagementandroid.data.dto.auth.response.LogoutResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthService {
    @POST("auth/login")
    Call<BaseResponse<LoginResponse>> login(@Body LoginRequest request);

    @GET("auth/logout")
    Call<BaseResponse<LogoutResponse>> logout();
}
