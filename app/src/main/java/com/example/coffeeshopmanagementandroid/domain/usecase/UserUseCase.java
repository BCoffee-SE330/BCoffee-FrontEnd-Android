package com.example.coffeeshopmanagementandroid.domain.usecase;

import android.util.Log;

import com.example.coffeeshopmanagementandroid.data.dto.user.request.UpdateUserRequest;
import com.example.coffeeshopmanagementandroid.domain.model.UserModel;
import com.example.coffeeshopmanagementandroid.domain.repository.UserRepository;

import java.io.File;
import java.util.Map;

public class UserUseCase {
    public final UserRepository userRepository;

    public UserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel getInformationCustomer() throws Exception {
        Log.d("User Use Case", "Called getInformationCustomer");
        return userRepository.getInformationCustomer();
    }

    public Map<String, String> uploadAvatar(File file) throws Exception {
        Log.d("User Use Case", "Called uploadAvatar");
        return userRepository.uploadAvatar(file);
    }

    public Map<String, String> deleteAvatar() throws Exception {
        Log.d("User Use Case", "Called deleteAvatar");
        return userRepository.deleteAvatar();
    }

    public UserModel updateUser(UpdateUserRequest request) throws Exception {
        Log.d("User Use Case", "Called updateUser");
        return userRepository.updateUser(request);
    }
}