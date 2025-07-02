package com.example.coffeeshopmanagementandroid.utils;

import android.content.Context;
import android.util.Log;

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

    private Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(context))
                    .authenticator(new TokenAuthenticator(context))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
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
                    .connectTimeout(60, TimeUnit.SECONDS) // Set connection timeout to 60 seconds
                    .readTimeout(60, TimeUnit.SECONDS)    // Set read timeout to 60 seconds
                    .writeTimeout(60, TimeUnit.SECONDS)   // Set write timeout to 60 seconds
                    .build();

            ragRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_RAG)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
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