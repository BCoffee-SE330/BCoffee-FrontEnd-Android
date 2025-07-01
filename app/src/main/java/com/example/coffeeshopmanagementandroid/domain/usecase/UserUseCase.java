package com.example.coffeeshopmanagementandroid.domain.usecase;

import android.util.Log;

import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.user.response.UserResponse;
import com.example.coffeeshopmanagementandroid.domain.model.UserModel;
import com.example.coffeeshopmanagementandroid.domain.repository.UserRepository;

public class UserUseCase {
    public final UserRepository userRepository;
    public UserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserModel getInformationCustomer() throws Exception {
        Log.d("User Use Case", "Called getInformationCustomer");
        return userRepository.getInformationCustomer();
    }
}
