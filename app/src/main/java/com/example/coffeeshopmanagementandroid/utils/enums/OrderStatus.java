package com.example.coffeeshopmanagementandroid.utils.enums;

import androidx.annotation.NonNull;

public enum OrderStatus {
    PENDING("ĐANG CHỜ"),
    PROCESSING("ĐANG XỬ LÝ"),
    COMPLETED("HOÀN TẤT"),
    DELIVERING("ĐANG GIAO HÀNG"),
    DELIVERED("ĐÃ GIAO HÀNG"),
    CANCELLED("ĐÃ HỦY"),;
    private final String status;
    OrderStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
    @NonNull
    @Override
    public String toString() {
        return status;
    }
}
