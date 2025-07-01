package com.example.coffeeshopmanagementandroid.domain.model.notification;

public class NotificationModel {
    private String id;
    private String notificationType;
    private String notificationContent;
    private String senderId;
    private String senderName;
    private String receiverId;
    private String receiverName;
    private boolean isRead;
    private String createdAt;
    private String updatedAt;

    public NotificationModel(String id, String notificationType, String notificationContent, String senderId, String senderName, String receiverId, String receiverName, boolean isRead, String createdAt, String updatedAt) {
        this.id = id;
        this.notificationType = notificationType;
        this.notificationContent = notificationContent;
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.isRead = isRead;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNotificationType() { return notificationType; }
    public void setNotificationType(String notificationType) { this.notificationType = notificationType; }

    public String getNotificationContent() { return notificationContent; }
    public void setNotificationContent(String notificationContent) { this.notificationContent = notificationContent; }

    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }

    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }

    public String getReceiverId() { return receiverId; }
    public void setReceiverId(String receiverId) { this.receiverId = receiverId; }

    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}