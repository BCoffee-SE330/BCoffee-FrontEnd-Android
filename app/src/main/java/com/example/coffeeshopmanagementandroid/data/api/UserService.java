package com.example.coffeeshopmanagementandroid.data.api;

import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.user.request.UpdateUserRequest;
import com.example.coffeeshopmanagementandroid.data.dto.user.response.UserResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserService {
    @GET("account/me")
    Call<BaseResponse<UserResponse>> getInformationCustomer();

    @Multipart
    @POST("account/avatar")
    Call<BaseResponse<Map<String, String>>> uploadAvatar(@Part MultipartBody.Part file);

    @DELETE("account/avatar")
    Call<BaseResponse<Map<String, String>>> deleteAvatar();

    @PATCH("account/me")
    Call<BaseResponse<UserResponse>> updateUser(@Body UpdateUserRequest request);
}
