package com.example.coffeeshopmanagementandroid.data.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.coffeeshopmanagementandroid.data.api.UserService;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.user.request.UpdateUserRequest;
import com.example.coffeeshopmanagementandroid.data.dto.user.response.UserResponse;
import com.example.coffeeshopmanagementandroid.data.mapper.UserMapper;
import com.example.coffeeshopmanagementandroid.domain.model.UserModel;
import com.example.coffeeshopmanagementandroid.domain.repository.UserRepository;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    @SuppressLint("LongLogTag")
    @Override
    public Map<String, String> uploadAvatar(File file) throws Exception {
        // Create MultipartBody.Part from File
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Call<BaseResponse<Map<String, String>>> call = userServices.uploadAvatar(filePart);
        Response<BaseResponse<Map<String, String>>> response = call.execute();

        if (response.isSuccessful() && response.body() != null) {
            return response.body().getData();
        } else {
            String errorMessage = "Upload avatar failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
            Log.e("UPLOAD AVATAR", errorMessage);
            throw new Exception(errorMessage);
        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public Map<String, String> deleteAvatar() throws Exception {
        Call<BaseResponse<Map<String, String>>> call = userServices.deleteAvatar();
        Response<BaseResponse<Map<String, String>>> response = call.execute();

        if (response.isSuccessful() && response.body() != null) {
            return response.body().getData();
        } else {
            String errorMessage = "Delete avatar failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
            Log.e("DELETE AVATAR", errorMessage);
            throw new Exception(errorMessage);
        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public UserModel updateUser(UpdateUserRequest request) throws Exception {
        Call<BaseResponse<UserResponse>> call = userServices.updateUser(request);
        Response<BaseResponse<UserResponse>> response = call.execute();

        if (response.isSuccessful() && response.body() != null) {
            return UserMapper.mapUserResponseToUserDomain(response.body().getData());
        } else {
            String errorMessage = "Update user failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
            Log.e("UPDATE USER", errorMessage);
            throw new Exception(errorMessage);
        }
    }
}