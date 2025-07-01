package com.example.coffeeshopmanagementandroid.domain.repository;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.category.request.GetAllCategoriesRequest;
import com.example.coffeeshopmanagementandroid.data.dto.category.response.CategoryResponse;

import java.util.List;

public interface CategoryRepository {
    BasePagingResponse<List<CategoryResponse>> getAllCategories(GetAllCategoriesRequest request) throws Exception;
}
