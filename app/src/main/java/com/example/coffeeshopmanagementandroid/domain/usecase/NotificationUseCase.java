package com.example.coffeeshopmanagementandroid.domain.usecase;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.notification.request.GetAllReceivedNotificationRequests;
import com.example.coffeeshopmanagementandroid.data.dto.notification.response.NotificationResponse;
import com.example.coffeeshopmanagementandroid.domain.repository.NotificationRepository;

import java.util.List;

public class NotificationUseCase {
    private final NotificationRepository notificationRepository;
    public NotificationUseCase(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    public BasePagingResponse<List<NotificationResponse>> getReceivedNotifications(GetAllReceivedNotificationRequests requests) throws Exception {
        return notificationRepository.getReceivedNotifications(requests);
    }
    public void markNotificationAsRead(String id) throws Exception {
        notificationRepository.markNotificationAsRead(id);
    }
    public void deleteNotification(String id) throws Exception {
        notificationRepository.deleteNotification(id);
    }

    public BaseResponse<NotificationResponse> getNotificationById(String id) throws Exception {
        return notificationRepository.getNotificationById(id);
    }
}
