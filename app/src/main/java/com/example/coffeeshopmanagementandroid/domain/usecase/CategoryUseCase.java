package com.example.coffeeshopmanagementandroid.domain.usecase;

import android.util.Log;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.category.request.GetAllCategoriesRequest;
import com.example.coffeeshopmanagementandroid.data.dto.category.response.CategoryResponse;
import com.example.coffeeshopmanagementandroid.domain.repository.CategoryRepository;

import java.util.List;

public class CategoryUseCase {
    private final CategoryRepository categoryRepository;
    public CategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public BasePagingResponse<List<CategoryResponse>> getAllCategories(GetAllCategoriesRequest request) throws Exception {
        Log.d("Category Use Case", "Called");
        return categoryRepository.getAllCategories(request);
    }
}
