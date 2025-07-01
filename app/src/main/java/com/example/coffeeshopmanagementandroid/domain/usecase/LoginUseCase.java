package com.example.coffeeshopmanagementandroid.domain.usecase;

import android.util.Log;
import android.util.Pair;

import com.example.coffeeshopmanagementandroid.domain.model.auth.AuthModel;
import com.example.coffeeshopmanagementandroid.domain.model.auth.UserModel;
import com.example.coffeeshopmanagementandroid.domain.repository.AuthRepository;

public class LoginUseCase {
    private final AuthRepository authRepository;

    public LoginUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Pair<AuthModel, UserModel> execute(String email, String password, Boolean rememberMe) throws Exception {
        Log.d("Login Use Case", "Called");
        return authRepository.login(email, password);
    }
}
