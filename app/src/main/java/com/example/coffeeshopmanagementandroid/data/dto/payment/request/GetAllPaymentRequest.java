package com.example.coffeeshopmanagementandroid.data.dto.payment.request;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingRequest;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.CartSortBy;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.PaymentSortBy;
import com.google.gson.annotations.SerializedName;

public class GetAllPaymentRequest extends BasePagingRequest {
    @SerializedName("sortType")
    private SortType sortType;
    @SerializedName("sortBy")
    private PaymentSortBy sortBy;

    public GetAllPaymentRequest(int page, int limit, SortType sortType, PaymentSortBy sortBy) {
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

    public PaymentSortBy getSortBy() {
        return sortBy;
    }

    public void setSortBy(PaymentSortBy sortBy) {
        this.sortBy = sortBy;
    }
}
