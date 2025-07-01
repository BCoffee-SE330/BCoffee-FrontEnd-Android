package com.example.coffeeshopmanagementandroid.data.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.coffeeshopmanagementandroid.data.api.UserService;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.user.response.UserResponse;
import com.example.coffeeshopmanagementandroid.data.mapper.UserMapper;
import com.example.coffeeshopmanagementandroid.domain.model.UserModel;
import com.example.coffeeshopmanagementandroid.domain.repository.UserRepository;

import retrofit2.Call;
import retrofit2.Response;

public class UserRepositoryImpl implements UserRepository {
    private final UserService userServices;
    public UserRepositoryImpl(UserService userServices) {
        this.userServices = userServices;
    }
    @SuppressLint("LongLogTag")
    @Override
    public UserModel getInformationCustomer() throws Exception {
        Call<BaseResponse<UserResponse>> call = userServices.getInformationCustomer();
        Response<BaseResponse<UserResponse>> response = call.execute();
        if (response.isSuccessful() && response.body() != null) {
            return UserMapper.mapUserResponseToUserDomain(response.body().getData());
        } else {
            String errorMessage = "Get information customer failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
            Log.e("GET INFORMATION CUSTOMER", errorMessage);
            throw new Exception(errorMessage);
        }
    }
}
