package com.example.coffeeshopmanagementandroid.utils.enums.sortBy;

import androidx.annotation.NonNull;

public enum PaymentSortBy {
    CREATED_AT("createdAt");

    private final String sortByField;

    PaymentSortBy(String sortByField) {
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
