package com.example.coffeeshopmanagementandroid.utils.enums;

public enum SortType {

    DESC("desc"),
    ASC("asc");

    private final String sortType;

    SortType(String sortType) {
        this.sortType = sortType;
    }

    public String getSortType() {
        return sortType;
    }

    @Override
    public String toString() {
        return sortType;
    }
}
