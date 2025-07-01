package com.example.coffeeshopmanagementandroid.domain.repository;

import android.util.Pair;

import com.example.coffeeshopmanagementandroid.data.dto.auth.response.LoginResponse;
import com.example.coffeeshopmanagementandroid.domain.model.auth.AuthModel;
import com.example.coffeeshopmanagementandroid.domain.model.auth.UserModel;

public interface AuthRepository {
    Pair<AuthModel, UserModel> login(String email, String password) throws Exception;

    String logout() throws Exception;
}