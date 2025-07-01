package com.example.coffeeshopmanagementandroid.data.mapper;

import com.example.coffeeshopmanagementandroid.data.dto.notification.response.NotificationResponse;
import com.example.coffeeshopmanagementandroid.domain.model.notification.NotificationModel;

import java.util.ArrayList;
import java.util.List;

public class NotificationMapper {
    public static NotificationModel mapToNotificationModel(NotificationResponse response) {
        if (response == null) return null;
        return new NotificationModel(
                response.getId(),
                response.getNotificationType(),
                response.getNotificationContent(),
                response.getSenderId(),
                response.getSenderName(),
                response.getReceiverId(),
                response.getReceiverName(),
                response.isRead(),
                response.getCreatedAt(),
                response.getUpdatedAt()
        );
    }

    public static List<NotificationModel> mapToNotificationModelList(List<NotificationResponse> responses) {
        List<NotificationModel> models = new ArrayList<>();
        if (responses != null) {
            for (NotificationResponse response : responses) {
                NotificationModel model = mapToNotificationModel(response);
                if (model != null) {
                    models.add(model);
                }
            }
        }
        return models;
    }
}