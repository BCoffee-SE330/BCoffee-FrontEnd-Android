package com.example.coffeeshopmanagementandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
    public static String getUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("id", null);
    }

    public static void saveUserPreferences(Context context, String userId, String role) {
        SharedPreferences prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        prefs.edit()
                .putString("id", userId)
                .putString("role", role)
                .apply();
    }
    public static void saveAuthPreferences(Context context, String accessToken, String refreshToken, boolean isLoggedIn) {
        SharedPreferences prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        prefs.edit()
                .putString("access_token", accessToken)
                .putString("refresh_token", refreshToken)
                .putBoolean("is_logged_in", isLoggedIn)
                .apply();
    }
}
