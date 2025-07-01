package com.example.coffeeshopmanagementandroid.data.mapper;

import com.example.coffeeshopmanagementandroid.data.dto.auth.response.LoginResponse;
import com.example.coffeeshopmanagementandroid.domain.model.auth.AuthModel;
import com.example.coffeeshopmanagementandroid.domain.model.auth.UserModel;
import com.example.coffeeshopmanagementandroid.utils.enums.Role;

public class AuthMapper {
    public static AuthModel mapLoginResponseToAuthDomain(LoginResponse response) {
        return new AuthModel(
                response.getAccessToken(),
                response.getRefreshToken()
        );
    }
    public static UserModel mapLoginResponseToUserDomain(LoginResponse response) {
        return new UserModel(
                response.getId(),
                Role.CUSTOMER
        );
    }
}
