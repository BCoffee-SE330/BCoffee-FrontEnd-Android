package com.example.coffeeshopmanagementandroid.data.dto.address.resquest;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingRequest;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.AddressSortBy;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.CartSortBy;
import com.google.gson.annotations.SerializedName;

public class GetAddressRequest extends BasePagingRequest {
    @SerializedName("sortType")
    private SortType sortType;
    @SerializedName("sortBy")
    private AddressSortBy sortBy;

    public GetAddressRequest(int page, int limit, SortType sortType, AddressSortBy sortBy) {
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

    public AddressSortBy getSortBy() {
        return sortBy;
    }

    public void setSortBy(AddressSortBy sortBy) {
        this.sortBy = sortBy;
    }
}
