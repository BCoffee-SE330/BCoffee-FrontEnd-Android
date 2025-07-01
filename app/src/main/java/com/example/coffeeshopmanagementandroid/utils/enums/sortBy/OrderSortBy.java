package com.example.coffeeshopmanagementandroid.utils.enums.sortBy;

import androidx.annotation.NonNull;

public enum OrderSortBy {
    STATUS("orderStatus"),
    ORDER_TOTAL_COST("orderTotalCost"),
    CREATED_AT("createdAt");
    private final String sortByField;
    OrderSortBy(String sortByField) {
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
