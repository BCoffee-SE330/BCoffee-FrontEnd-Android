package com.example.coffeeshopmanagementandroid.utils.enums.sortBy;

import androidx.annotation.NonNull;

public enum DiscountSortBy {
    NAME("discountName"),
    VALUE("discountValue"),
    TYPE("discountType"),
    CODE("discountCode"),
    START_DATE("discountStartDate"),
    END_DATE("discountEndDate"),
    MAX_USES("discountMaxUsers"),
    USER_COUNT("discountUserCount"),
    MAX_PER_USER("discountMaxPerUser"),
    MIN_ORDER_VALUE("discountMinOrderValue"),
    IS_ACTIVE("discountIsActive"),
    BRANCH_ID("branchId"),
    BRANCH_NAME("branchName"),
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt");

    private final String sortByField;

    DiscountSortBy(String sortByField) {
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