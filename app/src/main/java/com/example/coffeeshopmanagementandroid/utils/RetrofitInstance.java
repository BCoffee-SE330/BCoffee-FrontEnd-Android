package com.example.coffeeshopmanagementandroid.utils;

import android.content.Context;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.coffeeshopmanagementandroid.BuildConfig;
import com.example.coffeeshopmanagementandroid.data.api.RagService;

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
        if (ragRetrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(context))
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