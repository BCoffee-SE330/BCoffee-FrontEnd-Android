package com.example.coffeeshopmanagementandroid.data.api;

import com.example.coffeeshopmanagementandroid.data.dto.rag.request.QueryRequest;
import com.example.coffeeshopmanagementandroid.data.dto.rag.response.RagResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RagService {
    @POST("/query")
    Call<RagResponse> getRagResponse(@Body QueryRequest queryRequest);

    @POST("/query/stream")
    Call<RagResponse> getRagStreamResponse(@Body QueryRequest queryRequest);
}