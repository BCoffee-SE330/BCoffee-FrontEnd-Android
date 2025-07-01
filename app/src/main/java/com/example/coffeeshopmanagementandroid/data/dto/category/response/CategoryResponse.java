package com.example.coffeeshopmanagementandroid.data.dto.category.response;

public class CategoryResponse {
    private String id;
    private String categoryName;
    private String categoryDescription;

    public String getId() {
        return id;
    }

    public void setCategoryId(String categoryId) {
        this.id = categoryId;
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
}
