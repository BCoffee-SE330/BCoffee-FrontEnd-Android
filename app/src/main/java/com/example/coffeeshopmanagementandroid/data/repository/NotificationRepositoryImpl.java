package com.example.coffeeshopmanagementandroid.data.repository;

import com.example.coffeeshopmanagementandroid.data.api.NotificationService;
import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.notification.request.GetAllReceivedNotificationRequests;
import com.example.coffeeshopmanagementandroid.data.dto.notification.response.NotificationResponse;
import com.example.coffeeshopmanagementandroid.domain.repository.NotificationRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class NotificationRepositoryImpl implements NotificationRepository {
    private final NotificationService notificationService;

    public NotificationRepositoryImpl(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public BasePagingResponse<List<NotificationResponse>> getReceivedNotifications(GetAllReceivedNotificationRequests requests) throws Exception {
        try {
            Call<BasePagingResponse<List<NotificationResponse>>> call = notificationService.getReceivedNotifications(
                    requests.getPage(),
                    requests.getLimit(),
                    requests.getSortType().toString(),
                    requests.getSortBy().toString()
            );

            Response<BasePagingResponse<List<NotificationResponse>>> response = call.execute();

            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            } else {
                String errorMessage = "Get notifications failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
                throw new Exception(errorMessage);
            }
        } catch (Exception e) {
            throw new Exception("Failed to fetch notifications", e);
        }
    }

    @Override
    public void markNotificationAsRead(String id) throws Exception {
        try {
            notificationService.markNotificationAsRead(id).execute();
        } catch (Exception e) {
            throw new Exception("Failed to mark notification as read", e);
        }
    }

    @Override
    public void deleteNotification(String id) throws Exception {
        try {
            notificationService.deleteNotification(id).execute();
        } catch (Exception e) {
            throw new Exception("Failed to delete notification", e);
        }
    }

    @Override
    public BaseResponse<NotificationResponse> getNotificationById(String id) throws Exception {
        try {
            Call<BaseResponse<NotificationResponse>> call = notificationService.getNotificationById(id);
            Response<BaseResponse<NotificationResponse>> response = call.execute();

            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            } else {
                String errorMessage = "Get notification by ID failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
                throw new Exception(errorMessage);
            }
        } catch (Exception e) {
            throw new Exception("Failed to fetch notification by ID", e);
        }
    }
}
