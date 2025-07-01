package com.example.coffeeshopmanagementandroid.data.api;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.notification.response.NotificationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NotificationService {
    @GET("/notification/received/user")
    Call<BasePagingResponse<List<NotificationResponse>>> getReceivedNotifications(@Query("page") int page,
                                                                                  @Query("limit") int limit,
                                                                                  @Query("sortType") String sortType,
                                                                                  @Query("sortBy") String sortBy);

    @PATCH("/notification/read/{id}")
    Call<BaseResponse<Void>> markNotificationAsRead(@Path("id") String id);

    @DELETE("/notification/delete/{id}")
    Call<BaseResponse<Void>> deleteNotification(@Path("id") String id);

    @GET("/notification/{id}")
    Call<BaseResponse<NotificationResponse>> getNotificationById(@Path("id") String id);
}