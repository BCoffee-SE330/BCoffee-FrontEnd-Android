package com.example.coffeeshopmanagementandroid.data.dto.product.request;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingRequest;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.FavoriteProductSortBy;
import com.google.gson.annotations.SerializedName;

public class GetAllFavoriteProductsUserRequest extends BasePagingRequest {
    @SerializedName("sortType")
    private SortType sortType;
    @SerializedName("sortBy")
    private FavoriteProductSortBy sortBy;
    public GetAllFavoriteProductsUserRequest(int page, int limit, String id, SortType sortType, FavoriteProductSortBy sortBy) {
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

    public FavoriteProductSortBy getSortBy() {
        return sortBy;
    }

    public void setSortBy(FavoriteProductSortBy sortBy) {
        this.sortBy = sortBy;
    }
}
