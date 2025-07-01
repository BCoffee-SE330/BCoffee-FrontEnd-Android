package com.example.coffeeshopmanagementandroid.data.dto;

import com.google.gson.annotations.SerializedName;

public class BasePagingRequest {
    @SerializedName("page")
    private int page;
    @SerializedName("limit")
    private int limit;

    public BasePagingRequest(int page, int limit) {
        this.page = page;
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
