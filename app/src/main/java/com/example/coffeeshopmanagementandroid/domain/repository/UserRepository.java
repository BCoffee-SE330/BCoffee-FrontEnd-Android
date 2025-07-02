package com.example.coffeeshopmanagementandroid.domain.repository;

import com.example.coffeeshopmanagementandroid.data.dto.user.request.UpdateUserRequest;
import com.example.coffeeshopmanagementandroid.domain.model.UserModel;

import java.io.File;
import java.util.Map;

public interface UserRepository {
    UserModel getInformationCustomer() throws Exception;

    Map<String, String> uploadAvatar(File file) throws Exception;

    Map<String, String> deleteAvatar() throws Exception;

    UserModel updateUser(UpdateUserRequest request) throws Exception;
}