package com.example.coffeeshopmanagementandroid.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffeeshopmanagementandroid.data.dto.auth.response.RegisterResponse;
import com.example.coffeeshopmanagementandroid.domain.usecase.RegisterUseCase;

import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RegisterViewModel extends ViewModel {
    private final RegisterUseCase registerUseCase;

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> registerSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> successMessage = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Map<String, String>> validationErrors = new MutableLiveData<>();

    @Inject
    public RegisterViewModel(RegisterUseCase registerUseCase) {
        this.registerUseCase = registerUseCase;
    }

    // Getters for LiveData
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getRegisterSuccess() {
        return registerSuccess;
    }

    public LiveData<String> getSuccessMessage() {
        return successMessage;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Map<String, String>> getValidationErrors() {
        return validationErrors;
    }

    // Register method
    public void register(String email, String password, String confirmPassword, String firstName, String lastName, String phoneNumber, String gender,  String birthday) {
        setIsLoading(true);
        clearPreviousErrors();
        Log.d("RegisterViewModel", "Registering user with email: " + email);
        Log.d("RegisterViewModel", "Password: " + password);
        Log.d("RegisterViewModel", "Confirm Password: " + confirmPassword);
        Log.d("RegisterViewModel", "First Name: " + firstName);
        Log.d("RegisterViewModel", "Last Name: " + lastName);
        Log.d("RegisterViewModel", "Phone Number: " + phoneNumber);
        Log.d("RegisterViewModel", "Gender: " + gender);
        Log.d("RegisterViewModel", "Birthday: " + birthday);

        new Thread(() -> {
            try {
                RegisterResponse response = registerUseCase.execute(email, password, confirmPassword, firstName, lastName, gender, phoneNumber, birthday);

                if (response.isSuccess()) {
                    // Success case
                    registerSuccess.postValue(true);
                    successMessage.postValue(response.getData().getMessage());
                    Log.d("RegisterViewModel", "Registration successful: " + response.getData().getMessage());
                } else if (response.hasValidationErrors()) {
                    // Validation errors
                    validationErrors.postValue(response.getItems());
                    Log.d("RegisterViewModel", "Validation errors: " + response.getItems());
                } else {
                    // Other errors
                    errorMessage.postValue(response.getMessage());
                    Log.d("RegisterViewModel", "Registration error: " + response.getMessage());
                }
            } catch (Exception e) {
                errorMessage.postValue(e.getMessage());
                Log.e("RegisterViewModel", "Registration exception: " + e.getMessage(), e);
            } finally {
                setIsLoading(false);
            }
        }).start();
    }

    private void setIsLoading(Boolean loading) {
        isLoading.postValue(loading);
    }

    private void clearPreviousErrors() {
        errorMessage.postValue(null);
        validationErrors.postValue(null);
        successMessage.postValue(null);
        registerSuccess.postValue(null);
    }
}