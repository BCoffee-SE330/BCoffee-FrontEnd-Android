package com.example.coffeeshopmanagementandroid;

import android.app.Application;
import android.util.Log;

import com.google.firebase.FirebaseApp;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            FirebaseApp.initializeApp(this);
            Log.d("BaseApplication", "Firebase initialized successfully");
        } catch (Exception e) {
            Log.e("BaseApplication", "Firebase init error", e);
        }
    }
}