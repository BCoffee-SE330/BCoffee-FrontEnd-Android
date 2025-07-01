package com.example.coffeeshopmanagementandroid.data.mapper;

import com.example.coffeeshopmanagementandroid.data.dto.rag.response.RagResponse;
import com.example.coffeeshopmanagementandroid.domain.model.message.MessageModel;
import com.example.coffeeshopmanagementandroid.data.dto.rag.request.QueryRequest;

import java.util.ArrayList;
import java.util.List;

public class RagMapper {

    /**
     * Maps a RagResponse to a MessageModel representing an assistant response
     */
    public static MessageModel mapRagResponseToMessageModel(RagResponse ragResponse) {
        if (ragResponse == null || ragResponse.getResponse() == null) {
            return new MessageModel("assistant", "Sorry, I couldn't generate a response.", false);
        }

        return new MessageModel("assistant", ragResponse.getResponse(), false);
    }

    /**
     * Creates a MessageModel for user message
     */
    public static MessageModel createUserMessageModel(String userMessage) {
        return new MessageModel("user", userMessage, true);
    }

    /**
     * Maps chat history from MessageModel list to conversation history format for QueryRequest
     */
    public static List<QueryRequest.ConversationItem> mapMessageListToConversationHistory(
            List<MessageModel> messageList, int historyTurns) {

        List<QueryRequest.ConversationItem> history = new ArrayList<>();

        // Calculate the starting index to get the last N turns (pairs of messages)
        int startIndex = Math.max(0, messageList.size() - (historyTurns * 2));

        for (int i = startIndex; i < messageList.size(); i++) {
            MessageModel message = messageList.get(i);
            String role = message.isSent() ? "user" : "assistant";
            history.add(new QueryRequest.ConversationItem(role, message.getContent()));
        }

        return history;
    }
}