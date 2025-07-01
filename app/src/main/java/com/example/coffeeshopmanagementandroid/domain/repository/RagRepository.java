package com.example.coffeeshopmanagementandroid.domain.repository;

import com.example.coffeeshopmanagementandroid.data.dto.rag.request.QueryRequest;
import com.example.coffeeshopmanagementandroid.data.dto.rag.response.RagResponse;

import java.util.List;

public interface RagRepository {
    RagResponse getRagResponse(String query, List<QueryRequest.ConversationItem> conversationItems) throws Exception;
    RagResponse getRagStreamResponse(String query, List<QueryRequest.ConversationItem> conversationItems) throws Exception;
}
