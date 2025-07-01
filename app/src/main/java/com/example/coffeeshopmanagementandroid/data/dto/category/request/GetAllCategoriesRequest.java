package com.example.coffeeshopmanagementandroid.data.dto.category.request;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingRequest;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.CategorySortBy;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.google.gson.annotations.SerializedName;

public class GetAllCategoriesRequest extends BasePagingRequest {
    @SerializedName("sortType")
    private SortType sortType;
    @SerializedName("sortBy")
    private CategorySortBy sortBy;

    public GetAllCategoriesRequest(int page, int limit, SortType sortType, CategorySortBy sortBy) {
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

    public void setSortBy(CategorySortBy sortBy) {
        this.sortBy = sortBy;
    }
}