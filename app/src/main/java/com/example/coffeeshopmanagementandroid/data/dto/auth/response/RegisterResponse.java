package com.example.coffeeshopmanagementandroid.data.dto.auth.response;

import java.util.Map;

public class RegisterResponse {
    private int statusCode;
    private String message;
    private RegisterData data;
    private Map<String, String> items; // Cho validation errors

    public RegisterResponse() {
    }

    // Getters and Setters
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RegisterData getData() {
        return data;
    }

    public void setData(RegisterData data) {
        this.data = data;
    }

    public Map<String, String> getItems() {
        return items;
    }

    public void setItems(Map<String, String> items) {
        this.items = items;
    }

    // Utility methods
    public boolean isSuccess() {
        return statusCode == 200;
    }

    public boolean hasValidationErrors() {
        return statusCode == 0 && items != null && !items.isEmpty();
    }

    public static class RegisterData {
        private int statusCode;
        private String message;

        public RegisterData() {
        }

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}