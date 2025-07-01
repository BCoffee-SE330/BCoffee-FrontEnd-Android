package com.example.coffeeshopmanagementandroid.ui.viewmodel;

import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffeeshopmanagementandroid.domain.model.auth.AuthModel;
import com.example.coffeeshopmanagementandroid.domain.model.auth.UserModel;
import com.example.coffeeshopmanagementandroid.domain.usecase.LoginUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {
    private final LoginUseCase loginUseCase;
    private final MutableLiveData<AuthModel> authLiveData = new MutableLiveData<>();
    private final MutableLiveData<UserModel> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    @Inject
    public LoginViewModel(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    public LiveData<AuthModel> getAuthLiveData() {
        return authLiveData;
    }
    public void setAuthLiveData(AuthModel authModel) {
        authLiveData.postValue(authModel);
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }
    public void setErrorLiveData(String error) {
        errorLiveData.postValue(error);
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    public void setIsLoading(Boolean loading) {
        isLoading.postValue(loading);
    }

    public LiveData<UserModel> getUserLiveData() {
        return userLiveData;
    }

    public void setUserLiveData(UserModel user) {
        userLiveData.postValue(user);
    }


    public void login(String email, String password, Boolean rememberMe) {
        setIsLoading(true);
        new Thread(() -> {

            try {
                Pair<AuthModel, UserModel> authResponse = loginUseCase.execute(email, password, rememberMe);
                setAuthLiveData(authResponse.first);
                setUserLiveData(authResponse.second);
            } catch (Exception e) {
                errorLiveData.postValue(e.getMessage());
                Log.d("Error", String.valueOf(e));
            } finally {
                setIsLoading(false);
            }
        }).start();
    }
}
