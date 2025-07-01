package com.example.coffeeshopmanagementandroid.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.coffeeshopmanagementandroid.BuildConfig;
import com.example.coffeeshopmanagementandroid.ui.activity.AuthActivity;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {
    private final Context context;

    public TokenAuthenticator(Context context) {
        this.context = context.getApplicationContext();
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, Response response) throws IOException {
        // Prevent infinite loops
        if (responseCount(response) >= 2) return null;

        // Get refresh token
        SharedPreferences prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String refreshToken = prefs.getString("refresh_token", null);

        if (refreshToken == null) return null;

        // Call refresh endpoint (sync call using Retrofit or OkHttp directly)
        String newAccessToken = refreshAccessToken(refreshToken);
        if (newAccessToken == null) {
            // Handle refresh token failure
            logoutUser();
            return null;
        }

        // Save new token
        prefs.edit().putString("access_token", newAccessToken).apply();

        // Retry the original request with new token
        return response.request().newBuilder()
                .header("Authorization", "Bearer " + newAccessToken)
                .build();
    }

    private int responseCount(Response response) {
        int count = 1;
        while ((response = response.priorResponse()) != null) count++;
        return count;
    }

    private String refreshAccessToken(String refreshToken) {
        if (refreshToken == null) {
            Log.e("TokenAuthenticator", "Refresh token is null");
            return null;
        }

        // Sử dụng OkHttpClient mới không có AuthInterceptor để tránh vòng lặp
        OkHttpClient client = new OkHttpClient.Builder().build();

        // Tạo yêu cầu với header Authorization chứa refreshToken
        Request request = new Request.Builder()
                .url(BuildConfig.BASE_URL + "auth/refresh")
                .get() // Thay bằng POST nếu server yêu cầu body
                .header("Authorization", "Bearer " + refreshToken) // Đảm bảo gửi refreshToken trong header
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body() != null ? response.body().string() : "";
                return parseAccessToken(responseBody);
            } else {
                Log.e("TokenAuthenticator", "Refresh token failed with code: " + response.code() + ", body: " + response.body().string());
                return null;
            }
        } catch (IOException e) {
            Log.e("TokenAuthenticator", "Refresh token error: " + e.getMessage(), e);
            return null;
        }
    }

    private String parseAccessToken(String responseBody) {
        // TODO: Use proper JSON parsing, e.g., Gson or JSONObject
        try {
            org.json.JSONObject json = new org.json.JSONObject(responseBody);
            return json.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private void logoutUser() {
        // Clear tokens from SharedPreferences (logout the user)
        SharedPreferences prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("id");
        editor.remove("role");
        editor.remove("access_token");
        editor.remove("refresh_token");
        editor.putBoolean("is_logged_in", false);
        editor.apply();

        Intent intent = new Intent(context, AuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
