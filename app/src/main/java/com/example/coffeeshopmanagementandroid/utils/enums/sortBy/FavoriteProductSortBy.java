package com.example.coffeeshopmanagementandroid.utils.enums.sortBy;

public enum FavoriteProductSortBy {
    CREATED_AT("createdAt");
    private final String sortByField;
    FavoriteProductSortBy(String sortByField) {
        this.sortByField = sortByField;
    }
    public String getSortByField() {
        return sortByField;
    }

    @Override
    public String toString() {
        return sortByField;
    }
}
