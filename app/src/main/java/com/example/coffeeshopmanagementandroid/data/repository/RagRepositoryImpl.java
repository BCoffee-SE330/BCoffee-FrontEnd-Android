package com.example.coffeeshopmanagementandroid.data.repository;

import com.example.coffeeshopmanagementandroid.data.api.RagService;
import com.example.coffeeshopmanagementandroid.data.dto.rag.request.QueryRequest;
import com.example.coffeeshopmanagementandroid.data.dto.rag.response.RagResponse;
import com.example.coffeeshopmanagementandroid.domain.repository.RagRepository;

import java.util.List;

import retrofit2.Call;

public class RagRepositoryImpl implements RagRepository {

    private final RagService ragService;

    public RagRepositoryImpl(RagService ragService) {
        this.ragService = ragService;
    }

    @Override
    public RagResponse getRagResponse(String query, List<QueryRequest.ConversationItem> conversationItems) throws Exception {
        QueryRequest queryRequest = new QueryRequest(query, conversationItems);
        Call<RagResponse> call = ragService.getRagResponse(queryRequest);
        retrofit2.Response<RagResponse> response = call.execute();
        if (response.isSuccessful() && response.body() != null) {
            return response.body();
        } else {
            String errorMessage = "Get RAG response failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
            throw new Exception(errorMessage);
        }
    }

    @Override
    public RagResponse getRagStreamResponse(String query, List<QueryRequest.ConversationItem> conversationItems) throws Exception {
        QueryRequest queryRequest = new QueryRequest(query, conversationItems);
        Call<RagResponse> call = ragService.getRagStreamResponse(queryRequest);
        retrofit2.Response<RagResponse> response = call.execute();
        if (response.isSuccessful() && response.body() != null) {
            return response.body();
        } else {
            String errorMessage = "Get RAG stream response failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
            throw new Exception(errorMessage);
        }
    }
}
