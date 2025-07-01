package com.example.coffeeshopmanagementandroid.domain.usecase;

import com.example.coffeeshopmanagementandroid.data.dto.rag.request.QueryRequest;
import com.example.coffeeshopmanagementandroid.data.dto.rag.response.RagResponse;
import com.example.coffeeshopmanagementandroid.domain.repository.RagRepository;

import java.util.List;

public class RagUseCase {
    private final RagRepository ragRepository;

    public RagUseCase(RagRepository ragRepository) {
        this.ragRepository = ragRepository;
    }

    public RagResponse getRagResponse(String query, List<QueryRequest.ConversationItem> conversationItems) throws Exception {
        try {
            return ragRepository.getRagResponse(query, conversationItems);
        } catch (Exception e) {
            throw new Exception("Error getting RAG response: " + e.getMessage(), e);
        }
    }

    public RagResponse getRagStreamResponse(String query, List<QueryRequest.ConversationItem> conversationItems) throws Exception {
        try {
            return ragRepository.getRagStreamResponse(query, conversationItems);
        } catch (Exception e) {
            throw new Exception("Error getting RAG stream response: " + e.getMessage(), e);
        }
    }
}
