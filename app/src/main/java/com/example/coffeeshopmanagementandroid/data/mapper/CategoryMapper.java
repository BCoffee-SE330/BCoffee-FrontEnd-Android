package com.example.coffeeshopmanagementandroid.data.mapper;

import com.example.coffeeshopmanagementandroid.data.dto.category.response.CategoryResponse;
import com.example.coffeeshopmanagementandroid.domain.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {
    public static List<CategoryModel> mapCategoryResponsesToCategoriesDomain(List<CategoryResponse> categoryResponses) {
        List<CategoryModel> categoryModels = new ArrayList<>();
        for (CategoryResponse response : categoryResponses) {
            CategoryModel model = new CategoryModel();
            model.setCategoryId(response.getId());
            model.setCategoryName(response.getCategoryName());
            model.setCategoryDescription(response.getCategoryDescription());
            categoryModels.add(model);
        }
        return categoryModels;
    }
}
