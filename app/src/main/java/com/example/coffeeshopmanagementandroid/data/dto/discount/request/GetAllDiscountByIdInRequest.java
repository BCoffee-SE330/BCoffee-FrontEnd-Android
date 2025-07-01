package com.example.coffeeshopmanagementandroid.data.dto.discount.request;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingRequest;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.DiscountSortBy;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.OrderSortBy;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllDiscountByIdInRequest extends BasePagingRequest {
    @SerializedName("sortType")
    private SortType sortType;
    @SerializedName("sortBy")
    private DiscountSortBy sortBy;

    @SerializedName("discountIds")
    private List<String> discountIds;

    public GetAllDiscountByIdInRequest(int page, int limit, SortType sortType, DiscountSortBy sortBy, List<String> discountIds) {
        super(page, limit);
        this.sortType = sortType;
        this.sortBy = sortBy;
        this.discountIds = discountIds;
    }

    public SortType getSortType() {
        return sortType;
    }
    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }
    public DiscountSortBy getSortBy() {
        return sortBy;
    }
    public void setSortBy(DiscountSortBy sortBy) {
        this.sortBy = sortBy;
    }
    public List<String> getDiscountIds() {
        return discountIds;
    }
    public void setDiscountIds(List<String> discountIds) {
        this.discountIds = discountIds;
    }
}
