package com.example.coffeeshopmanagementandroid.domain.model.message;

public class MessageModel {
    private String content;
    private boolean isSent;

    public MessageModel(String content, boolean isSent) {
        this.content = content;
        this.isSent = isSent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }
}