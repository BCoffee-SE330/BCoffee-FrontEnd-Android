package com.example.coffeeshopmanagementandroid.data.dto.auth.request;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("email")
    private final String email;
    @SerializedName("password")
    private final String password;

    @SerializedName("firebaseToken")
    private String firebaseToken;

    public LoginRequest(String email, String password, String firebaseToken) {
        this.email = email;
        this.password = password;
        this.firebaseToken = firebaseToken;
    }
}