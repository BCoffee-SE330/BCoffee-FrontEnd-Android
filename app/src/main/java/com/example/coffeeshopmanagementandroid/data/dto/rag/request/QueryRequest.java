package com.example.coffeeshopmanagementandroid.data.dto.rag.request;

import java.util.ArrayList;
import java.util.List;

public class QueryRequest {
    private String mode;
    private String response_type;
    private int top_k;
    private int max_token_for_text_unit;
    private int max_token_for_global_context;
    private int max_token_for_local_context;
    private boolean only_need_context;
    private boolean only_need_prompt;
    private boolean stream;
    private int history_turns;
    private List<String> hl_keywords;
    private List<String> ll_keywords;
    private String user_prompt;
    private String query;
    private List<ConversationItem> conversation_history;

    public static class ConversationItem {
        private String role;
        private String content;

        // Constructor
        public ConversationItem(String role, String content) {
            this.role = role;
            this.content = content;
        }

        // Getters and setters
        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public QueryRequest(String content, List<ConversationItem> conversation_history) {
        this.mode = "global";
        this.response_type = "Multiple Paragraphs";
        this.top_k = 10;
        this.max_token_for_text_unit = 4000;
        this.max_token_for_global_context = 4000;
        this.max_token_for_local_context = 4000;
        this.only_need_context = false;
        this.only_need_prompt = false;
        this.stream = false;
        this.history_turns = 3;
        this.hl_keywords = new ArrayList<>();
        this.ll_keywords = new ArrayList<>();
        this.user_prompt = "";
        this.query = content;
        this.conversation_history = conversation_history;
    }

    // Getters and setters
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getResponse_type() {
        return response_type;
    }

    public void setResponse_type(String response_type) {
        this.response_type = response_type;
    }

    public int getTop_k() {
        return top_k;
    }

    public void setTop_k(int top_k) {
        this.top_k = top_k;
    }

    public int getMax_token_for_text_unit() {
        return max_token_for_text_unit;
    }

    public void setMax_token_for_text_unit(int max_token_for_text_unit) {
        this.max_token_for_text_unit = max_token_for_text_unit;
    }

    public int getMax_token_for_global_context() {
        return max_token_for_global_context;
    }

    public void setMax_token_for_global_context(int max_token_for_global_context) {
        this.max_token_for_global_context = max_token_for_global_context;
    }

    public int getMax_token_for_local_context() {
        return max_token_for_local_context;
    }

    public void setMax_token_for_local_context(int max_token_for_local_context) {
        this.max_token_for_local_context = max_token_for_local_context;
    }

    public boolean isOnly_need_context() {
        return only_need_context;
    }

    public void setOnly_need_context(boolean only_need_context) {
        this.only_need_context = only_need_context;
    }

    public boolean isOnly_need_prompt() {
        return only_need_prompt;
    }

    public void setOnly_need_prompt(boolean only_need_prompt) {
        this.only_need_prompt = only_need_prompt;
    }

    public boolean isStream() {
        return stream;
    }

    public void setStream(boolean stream) {
        this.stream = stream;
    }

    public int getHistory_turns() {
        return history_turns;
    }

    public void setHistory_turns(int history_turns) {
        this.history_turns = history_turns;
    }

    public List<String> getHl_keywords() {
        return hl_keywords;
    }

    public void setHl_keywords(List<String> hl_keywords) {
        this.hl_keywords = hl_keywords;
    }

    public List<String> getLl_keywords() {
        return ll_keywords;
    }

    public void setLl_keywords(List<String> ll_keywords) {
        this.ll_keywords = ll_keywords;
    }

    public String getUser_prompt() {
        return user_prompt;
    }

    public void setUser_prompt(String user_prompt) {
        this.user_prompt = user_prompt;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<ConversationItem> getConversation_history() {
        return conversation_history;
    }

    public void setConversation_history(List<ConversationItem> conversation_history) {
        this.conversation_history = conversation_history;
    }
}