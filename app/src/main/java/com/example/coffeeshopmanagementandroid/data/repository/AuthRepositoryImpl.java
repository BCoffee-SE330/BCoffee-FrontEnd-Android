package com.example.coffeeshopmanagementandroid.data.repository;
import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.example.coffeeshopmanagementandroid.data.api.AuthService;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.auth.request.LoginRequest;
import com.example.coffeeshopmanagementandroid.data.dto.auth.response.LoginResponse;
import com.example.coffeeshopmanagementandroid.data.dto.auth.response.LogoutResponse;
import com.example.coffeeshopmanagementandroid.data.mapper.AuthMapper;
import com.example.coffeeshopmanagementandroid.domain.model.auth.AuthModel;
import com.example.coffeeshopmanagementandroid.domain.model.auth.UserModel;
import com.example.coffeeshopmanagementandroid.domain.repository.AuthRepository;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import dagger.hilt.android.qualifiers.ApplicationContext;
import retrofit2.Call;
import retrofit2.Response;


public class AuthRepositoryImpl implements AuthRepository {
    private static final String TAG = "AuthRepoImpl";
    private static final String PREF_NAME = "fcm_prefs";
    private static final String KEY_FCM_TOKEN = "fcm_token";

    private final AuthService authService;
    private final Context context;

    public AuthRepositoryImpl(
            AuthService authService,
            @ApplicationContext Context context
    ) {
        this.authService = authService;
        this.context = context;

        if (context != null) {
            initializeFcmToken();
        } else {
            Log.e(TAG, "Context is null in constructor!");
        }
    }

    private void initializeFcmToken() {
        if (context == null) {
            Log.e(TAG, "Context is null in initializeFcmToken!");
            return;
        }

        // Lấy token trong background
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        cacheFcmToken(task.getResult());
                        Log.d(TAG, "FCM token initialized: " + task.getResult());
                    } else {
                        Log.e(TAG, "FCM token initialization failed", task.getException());
                    }
                });
    }

    private String getCachedFcmToken() {
        if (context == null) {
            Log.e(TAG, "Context is null in getCachedFcmToken!");
            return null;
        }
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_FCM_TOKEN, null);
    }

    private void cacheFcmToken(String token) {
        if (context == null) {
            Log.e(TAG, "Context is null in getCachedFcmToken!");
            return;
        }
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_FCM_TOKEN, token).apply();
    }

    @Override
    public Pair<AuthModel, UserModel> login(String email, String password) throws Exception {
        Log.d("AuthRepoImpl", "Login called");

        // 1. Sử dụng token đã cache nếu có
        String cachedToken = getCachedFcmToken();
        Log.d(TAG, "Using cached token: " + (cachedToken != null));

        // 2. Cố gắng lấy token mới nhưng không chờ quá lâu
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicReference<String> tokenRef = new AtomicReference<>(cachedToken);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String newToken = task.getResult();
                        tokenRef.set(newToken);
                        cacheFcmToken(newToken);
                        Log.d(TAG, "New FCM token: " + newToken);
                    } else {
                        Log.w(TAG, "FCM token update failed", task.getException());
                    }
                    latch.countDown();
                });

        // Chờ tối đa 3 giây cho token mới
        try {
            latch.await(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Log.w(TAG, "Token wait interrupted", e);
            Thread.currentThread().interrupt();
        }

        String firebaseToken = tokenRef.get();
        Log.d(TAG, "Final token for login: " + firebaseToken);

        // 3. Gọi API login với token
        Call<BaseResponse<LoginResponse>> call = authService.login(
                new LoginRequest(email, password, firebaseToken)
        );

        Response<BaseResponse<LoginResponse>> response = call.execute();

        Log.d("LOGIN", "Response received: " + response);

        if (response.isSuccessful() && response.body() != null) {
            BaseResponse<LoginResponse> baseResponse = response.body();
            LoginResponse data = baseResponse.getData();

            if (baseResponse.getData() != null) {
                Log.d("Response", "Login ID: " + data.getId());
                UserModel user = AuthMapper.mapLoginResponseToUserDomain(data);
                AuthModel authModel = AuthMapper.mapLoginResponseToAuthDomain(data);
                return new Pair<>(authModel, user);
            } else {
                throw new Exception("Login failed: empty data");
            }
        } else {
            String errorMessage = "Login failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
            Log.e("LOGIN", errorMessage);
            throw new Exception(errorMessage);
        }
    }

    @Override
    public String logout() throws Exception {
        Log.d("AuthRepoImpl", "Logout called");

        // Xóa token cache khi đăng xuất
        clearFcmTokenCache();

        Call<BaseResponse<LogoutResponse>> call = authService.logout();
        Response<BaseResponse<LogoutResponse>> response = call.execute();

        if (response.isSuccessful() && response.body() != null) {
            BaseResponse<LogoutResponse> baseResponse = response.body();

            if (baseResponse.getData() != null) {
                Log.d("Response", "Logout message: " + baseResponse.getMessage());
                return baseResponse.getMessage();
            } else {
                throw new Exception("Logout failed: empty data");
            }
        } else {
            String errorMessage = "Logout failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
            Log.e("LOGOUT", errorMessage);
            throw new Exception(errorMessage);
        }
    }

    private void clearFcmTokenCache() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY_FCM_TOKEN).apply();
    }

}
