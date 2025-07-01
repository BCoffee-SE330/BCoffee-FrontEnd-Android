package com.example.coffeeshopmanagementandroid.data.dto.product.request;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingRequest;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.ProductSortBy;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.ProductVariantSortBy;
import com.google.gson.annotations.SerializedName;

public class GetAllProductVariantsRequest extends BasePagingRequest {

    @SerializedName("productId")
    private String productId;
    @SerializedName("sortType")
    private SortType sortType;
    @SerializedName("sortBy")
    private ProductVariantSortBy sortBy;

    public GetAllProductVariantsRequest(String productId, int page, int limit, SortType sortType, ProductVariantSortBy sortBy) {
        super(page, limit);
        this.productId = productId;
        this.sortType = sortType;
        this.sortBy = sortBy;
    }
    public String getProductId() {
        return productId;
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

    public void setSortBy(ProductVariantSortBy sortBy) {
        this.sortBy = sortBy;
    }
}
