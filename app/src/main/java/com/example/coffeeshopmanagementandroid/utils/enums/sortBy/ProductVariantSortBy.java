package com.example.coffeeshopmanagementandroid.utils.enums.sortBy;

import androidx.annotation.NonNull;

public enum ProductVariantSortBy {
    NAME("productName"),
    PRICE("productPrice"),
    RATING("productRatingsAverage"),
    CREATED_AT("createdAt");

    private final String sortByField;

    ProductVariantSortBy(String sortByField) {
        this.sortByField = sortByField;
    }

    public String getSortByField() {
        return sortByField;
    }

    @NonNull
    @Override
    public String toString() {
        return sortByField;
    }
}
