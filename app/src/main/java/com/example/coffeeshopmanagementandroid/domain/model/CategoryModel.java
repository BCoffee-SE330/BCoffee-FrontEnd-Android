package com.example.coffeeshopmanagementandroid.domain.model;

import java.util.Objects;

public class CategoryModel {
    private String categoryId;
    private String categoryName;
    private String categoryDescription;

    public CategoryModel() {

    }

    public CategoryModel(String categoryId, String categoryName, String categoryDescription) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryModel that = (CategoryModel) o;
        return categoryId.equals(that.categoryId) &&
                categoryName.equals(that.categoryName) &&
                categoryDescription.equals(that.categoryDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                categoryId,
                categoryName,
                categoryDescription
        );
    }
}
