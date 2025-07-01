package com.example.coffeeshopmanagementandroid.utils;

import android.content.Context;

import com.example.coffeeshopmanagementandroid.ui.component.LoadingDialog;

public class LoadingManager {
    private static LoadingManager instance;
    private LoadingDialog loadingDialog;

    private LoadingManager() {
        // Private constructor to prevent instantiation
    }

    public static synchronized LoadingManager getInstance() {
        if (instance == null) {
            instance = new LoadingManager();
        }
        return instance;
    }

    public void showLoading(Context context) {
        if (loadingDialog == null || !loadingDialog.isShowing()) {
            loadingDialog = new LoadingDialog(context);
            loadingDialog.show();
        }
    }

    public void hideLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null; // Reset to avoid memory leaks
        }
    }
}