package com.example.coffeeshopmanagementandroid.domain.usecase;

import android.util.Log;

import com.example.coffeeshopmanagementandroid.data.dto.auth.request.RegisterRequest;
import com.example.coffeeshopmanagementandroid.data.dto.auth.response.RegisterResponse;
import com.example.coffeeshopmanagementandroid.domain.repository.AuthRepository;

import javax.inject.Inject;

public class RegisterUseCase {
    private final AuthRepository authRepository;

    @Inject
    public RegisterUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public RegisterResponse execute(String email, String password, String confirmPassword, String firstName, String lastName, String gender, String phoneNumber, String birthday) throws Exception {
        try {
            RegisterRequest request = new RegisterRequest(email, password, confirmPassword, firstName, lastName, phoneNumber, gender, birthday);
            Log.d("RegisterUseCase", "Request: " + request);
            return authRepository.register(request);
        } catch (Exception e) {
            throw new Exception("Error registering user: " + e.getMessage());
        }
    }
}