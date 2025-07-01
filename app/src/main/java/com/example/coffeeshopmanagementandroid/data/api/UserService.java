package com.example.coffeeshopmanagementandroid.data.api;

import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.user.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {
    @GET("account/me")
    Call<BaseResponse<UserResponse>> getInformationCustomer();
}
