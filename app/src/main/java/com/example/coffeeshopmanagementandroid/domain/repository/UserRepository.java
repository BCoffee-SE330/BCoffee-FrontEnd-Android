package com.example.coffeeshopmanagementandroid.domain.repository;

import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.user.response.UserResponse;
import com.example.coffeeshopmanagementandroid.domain.model.UserModel;

public interface UserRepository {
    UserModel getInformationCustomer() throws Exception;
}
