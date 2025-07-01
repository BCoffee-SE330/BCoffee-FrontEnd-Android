package com.example.coffeeshopmanagementandroid.domain.model;

public class OrderStatusModel {
    private String statusName;

    public OrderStatusModel(String statusName) {
        this.statusName = statusName;
    }

    // Getter và setter cho statusName
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
