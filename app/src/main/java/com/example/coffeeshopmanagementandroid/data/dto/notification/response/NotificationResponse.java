package com.example.coffeeshopmanagementandroid.data.dto.notification.response;

import com.google.gson.annotations.SerializedName;

public class NotificationResponse {
    @SerializedName("id")
    private String id;

    @SerializedName("notificationType")
    private String notificationType;

    @SerializedName("notificationContent")
    private String notificationContent;

    @SerializedName("senderId")
    private String senderId;

    @SerializedName("senderName")
    private String senderName;

    @SerializedName("receiverId")
    private String receiverId;

    @SerializedName("receiverName")
    private String receiverName;

    @SerializedName("read")
    private boolean isRead;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    // Getters and setters
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