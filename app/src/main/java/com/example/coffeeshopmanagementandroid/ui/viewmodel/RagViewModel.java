package com.example.coffeeshopmanagementandroid.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffeeshopmanagementandroid.data.dto.rag.request.QueryRequest;
import com.example.coffeeshopmanagementandroid.data.dto.rag.response.RagResponse;
import com.example.coffeeshopmanagementandroid.data.mapper.RagMapper;
import com.example.coffeeshopmanagementandroid.domain.model.message.MessageModel;
import com.example.coffeeshopmanagementandroid.domain.usecase.RagUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RagViewModel extends ViewModel {
    private final RagUseCase ragUseCase;

    private MutableLiveData<List<MessageModel>> messagesLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    @Inject
    public RagViewModel(RagUseCase ragUseCase) {
        this.ragUseCase = ragUseCase;
    }

    public MutableLiveData<List<MessageModel>> getMessagesLiveData() {
        return messagesLiveData;
    }

    public void setMessagesLiveData(List<MessageModel> messages) {
        messagesLiveData.postValue(messages);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(Boolean loading) {
        isLoading.postValue(loading);
    }

    public void sendMessage(String message) {
        setIsLoading(true);
        new Thread(() -> {
            try {
                List<QueryRequest.ConversationItem> conversationItems = RagMapper.mapMessageListToConversationHistory(messagesLiveData.getValue(), 3);
                RagResponse response = ragUseCase.getRagResponse(message, conversationItems);
                MessageModel messageModel = RagMapper.mapRagResponseToMessageModel(response);
                addMessageToMessagesLiveData(messageModel);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                setIsLoading(false);
            }
        }).start();
    }

    public void sendMessageStream(String message) {
        setIsLoading(true);
        new Thread(() -> {
            try {
                List<QueryRequest.ConversationItem> conversationItems = RagMapper.mapMessageListToConversationHistory(messagesLiveData.getValue(), 3);
                RagResponse response = ragUseCase.getRagStreamResponse(message, conversationItems);
                MessageModel messageModel = RagMapper.mapRagResponseToMessageModel(response);
                addMessageToMessagesLiveData(messageModel);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                setIsLoading(false);
            }
        }).start();
    }

    public void addMessageToMessagesLiveData(MessageModel message) {
        List<MessageModel> currentMessages = messagesLiveData.getValue();
        if (currentMessages != null) {
            currentMessages.add(message);
            messagesLiveData.postValue(currentMessages);
        } else {
            messagesLiveData.postValue(List.of(message));
        }
    }
}
