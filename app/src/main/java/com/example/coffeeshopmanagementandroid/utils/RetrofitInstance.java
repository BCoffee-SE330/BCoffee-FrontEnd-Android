package com.example.coffeeshopmanagementandroid.utils;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.coffeeshopmanagementandroid.BuildConfig;
import com.example.coffeeshopmanagementandroid.data.api.RagService;

import java.util.concurrent.TimeUnit;

public class RetrofitInstance {
    private static Retrofit retrofit;
    private static Retrofit ragRetrofit;
    private static final String BASE_URL = BuildConfig.BASE_URL;
    private static final String BASE_URL_RAG = BuildConfig.BASE_URL_RAG;
    private final Context context;

    public RetrofitInstance(Context context) {
        this.context = context;
    }

    private Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter());
        }
        return gsonBuilder.create();
    }

    private Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(context))
                    .authenticator(new TokenAuthenticator(context))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(createGson()))
                    .build();
        }
        return retrofit;
    }

    private Retrofit getRagRetrofitInstance() {
        Log.d("getRagRetrofitInstance", "BASE_URL_RAG: " + BASE_URL_RAG);
        if (ragRetrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addInterceptor(chain -> {
                        Request request = chain.request().newBuilder()
                                .addHeader("Content-Type", "application/json; charset=UTF-8")
                                .addHeader("Accept", "application/json; charset=UTF-8")
                                .build();
                        return chain.proceed(request);
                    })
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();

            ragRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_RAG)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(createGson()))
                    .build();
        }
        return ragRetrofit;
    }

    public <T> T createService(Class<T> serviceClass) {
        if (serviceClass == RagService.class) {
            return getRagRetrofitInstance().create(serviceClass);
        }
        return getRetrofitInstance().create(serviceClass);
    }
}