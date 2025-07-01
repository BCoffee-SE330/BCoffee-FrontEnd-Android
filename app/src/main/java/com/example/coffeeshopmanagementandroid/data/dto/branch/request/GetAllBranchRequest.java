package com.example.coffeeshopmanagementandroid.data.dto.branch.request;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingRequest;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.OrderSortBy;
import com.google.gson.annotations.SerializedName;

public class GetAllBranchRequest extends BasePagingRequest {
    @SerializedName("sortType")
    private SortType sortType;
    @SerializedName("sortBy")
    private OrderSortBy sortBy;
    public GetAllBranchRequest(int page, int limit, SortType sortType, OrderSortBy sortBy) {
        super(page, limit);
        this.sortType = sortType;
        this.sortBy = sortBy;
    }
    public SortType getSortType() {
        return sortType;
    }

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    public OrderSortBy getSortBy() {
        return sortBy;
    }

    public void setSortBy(OrderSortBy sortBy) {
        this.sortBy = sortBy;
    }
}
