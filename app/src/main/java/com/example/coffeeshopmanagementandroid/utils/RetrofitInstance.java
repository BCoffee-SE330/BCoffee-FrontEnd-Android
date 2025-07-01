package com.example.coffeeshopmanagementandroid.utils;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.example.coffeeshopmanagementandroid.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = BuildConfig.BASE_URL;
    private final Context context;

    public RetrofitInstance(Context context) {
        this.context = context.getApplicationContext();
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            // Thêm interceptor để log request/response
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d("API_LOG", message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(context))
                    .authenticator(new TokenAuthenticator(context))
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();

            // Sử dụng định dạng ngày tương thích với server (ISO 8601 không timezone)
            Gson gson = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                        .create();
            }

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson)) // Dùng Gson custom
                    .build();
        }
        return retrofit;
    }

    public <T> T createService(Class<T> serviceClass) {
        return getRetrofit().create(serviceClass);
    }
}
