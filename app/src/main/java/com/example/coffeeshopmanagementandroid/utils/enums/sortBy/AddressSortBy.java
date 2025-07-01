package com.example.coffeeshopmanagementandroid.utils.enums.sortBy;

import androidx.annotation.NonNull;

public enum AddressSortBy {
    CREATED_AT("createdAt");

    private final String sortByField;

    AddressSortBy(String sortByField) {
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
