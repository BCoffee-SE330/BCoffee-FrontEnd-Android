package com.example.coffeeshopmanagementandroid.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffeeshopmanagementandroid.domain.model.UserModel;
import com.example.coffeeshopmanagementandroid.domain.usecase.UserUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UserViewModel extends ViewModel {
    private final UserUseCase userUseCase;
    private final MutableLiveData<UserModel> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    @Inject
    public UserViewModel(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    public MutableLiveData<UserModel> getUserLiveData() {
        return userLiveData;
    }

    public void setUserResult(UserModel user) {
        userLiveData.postValue(user);
    }


    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void setErrorLiveData(String errorMessage) {
        errorLiveData.postValue(errorMessage);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }


    public void setIsLoading(Boolean loading) {
        isLoading.postValue(loading);
    }

    public void fetchInformationCustomer() {
        setIsLoading(true);
        new Thread(() -> {
            try {
                UserModel result = userUseCase.getInformationCustomer();
                if (result != null) {
                    setUserResult(result);
                }
            } catch (Exception e) {
                setErrorLiveData(e.getMessage());
                Log.e("UserViewModel", "Fetch information failed: " + e.getMessage(), e);
            } finally {
                setIsLoading(false);
            }
        }).start();
    }
}
