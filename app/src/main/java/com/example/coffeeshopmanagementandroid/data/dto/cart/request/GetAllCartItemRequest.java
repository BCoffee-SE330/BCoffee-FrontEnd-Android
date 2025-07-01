package com.example.coffeeshopmanagementandroid.data.dto.cart.request;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingRequest;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.CartSortBy;
import com.google.gson.annotations.SerializedName;

public class GetAllCartItemRequest extends BasePagingRequest {
    @SerializedName("sortType")
    private SortType sortType;
    @SerializedName("sortBy")
    private CartSortBy sortBy;

    public GetAllCartItemRequest(int page, int limit, SortType sortType, CartSortBy sortBy) {
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

    public CartSortBy getSortBy() {
        return sortBy;
    }

    public void setSortBy(CartSortBy sortBy) {
        this.sortBy = sortBy;
    }
}
