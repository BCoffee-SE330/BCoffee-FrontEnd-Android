package com.example.coffeeshopmanagementandroid.utils;

import android.content.Context;

import com.example.coffeeshopmanagementandroid.data.api.RagService;
import com.example.coffeeshopmanagementandroid.data.dto.rag.request.QueryRequest;
import com.example.coffeeshopmanagementandroid.data.dto.rag.response.RagResponse;

import java.util.List;

public class RagServiceManager {
    private static RagServiceManager instance;
    private final RagService ragService;

    public interface RagCallback {
        void onSuccess(String response);
        void onError(String errorMessage);
    }

    private RagServiceManager(Context context) {
        RetrofitInstance retrofitInstance = new RetrofitInstance(context);
        this.ragService = retrofitInstance.createService(RagService.class);
    }

    public static synchronized RagServiceManager getInstance(Context context) {
        if (instance == null) {
            instance = new RagServiceManager(context.getApplicationContext());
        }
        return instance;
    }

    public static RagServiceManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("RagServiceManager must be initialized with context first");
        }
        return instance;
    }

    public void sendMessage(String query, List<QueryRequest.ConversationItem> conversationItems, RagCallback callback) {
        new Thread(() -> {
            try {
                conversationItems.remove(conversationItems.size() - 1);
                QueryRequest queryRequest = new QueryRequest(query, conversationItems);
                retrofit2.Call<RagResponse> call = ragService.getRagResponse(queryRequest);
                retrofit2.Response<RagResponse> response = call.execute();

                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getResponse());
                } else {
                    String errorMessage = response.errorBody() != null ?
                            response.errorBody().string() : "Unknown error";
                    callback.onError(errorMessage);
                }
            } catch (Exception e) {
                callback.onError(e.getMessage());
            }
        }).start();
    }

    public void sendStreamMessage(String query, List<QueryRequest.ConversationItem> conversationItems, RagCallback callback) {
        new Thread(() -> {
            try {
                QueryRequest queryRequest = new QueryRequest(query, conversationItems);
                queryRequest.setStream(true);
                retrofit2.Call<RagResponse> call = ragService.getRagStreamResponse(queryRequest);
                retrofit2.Response<RagResponse> response = call.execute();

                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getResponse());
                } else {
                    String errorMessage = response.errorBody() != null ?
                            response.errorBody().string() : "Unknown error";
                    callback.onError(errorMessage);
                }
            } catch (Exception e) {
                callback.onError(e.getMessage());
            }
        }).start();
    }
}