package com.example.coffeeshopmanagementandroid.domain.usecase;

import android.util.Log;

import com.example.coffeeshopmanagementandroid.domain.repository.AuthRepository;

public class LogoutUseCase {
    private final AuthRepository authRepository;

    public LogoutUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public String execute() throws Exception {
        Log.d("Logout Use Case", "Called");
        return authRepository.logout();
    }

}
