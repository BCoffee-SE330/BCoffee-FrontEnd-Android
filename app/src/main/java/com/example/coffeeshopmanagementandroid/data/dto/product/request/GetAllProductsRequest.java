package com.example.coffeeshopmanagementandroid.data.dto.product.request;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingRequest;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.ProductSortBy;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.google.gson.annotations.SerializedName;

public class GetAllProductsRequest extends BasePagingRequest {

    @SerializedName("sortType")
    private SortType sortType;
    @SerializedName("sortBy")
    private ProductSortBy sortBy;

    public GetAllProductsRequest(int page, int limit, SortType sortType, ProductSortBy sortBy) {
        super(page, limit);
        this.sortType = sortType;
        this.sortBy = sortBy;
    }

    public String getSortType() {
        return sortType.toString();
    }

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    public String getSortBy() {
        return sortBy.toString();
    }

    public void setSortBy(ProductSortBy sortBy) {
        this.sortBy = sortBy;
    }
}
