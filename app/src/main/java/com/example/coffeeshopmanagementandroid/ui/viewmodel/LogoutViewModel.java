package com.example.coffeeshopmanagementandroid.ui.viewmodel;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffeeshopmanagementandroid.domain.usecase.LogoutUseCase;
import com.example.coffeeshopmanagementandroid.utils.LoadingManager;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LogoutViewModel extends ViewModel {
    private final LogoutUseCase logoutUseCase;
    private final MutableLiveData<String> logoutResult = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    @Inject
    public LogoutViewModel(LogoutUseCase logoutUseCase) {
        this.logoutUseCase = logoutUseCase;
    }
    public LiveData<String> getLogoutResult() {
        return logoutResult;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void logout() {
        isLoading.postValue(true);
        new Thread(() -> {
            try {
                String result = logoutUseCase.execute();
                logoutResult.postValue(result);
            } catch (Exception e) {
                errorLiveData.postValue(e.getMessage());
                Log.e("LogoutViewModel", "Logout failed: " + e.getMessage(), e);
            } finally {
                isLoading.postValue(false);
            }
        }).start();
    }
}
