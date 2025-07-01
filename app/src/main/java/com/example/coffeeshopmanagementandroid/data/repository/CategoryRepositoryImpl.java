package com.example.coffeeshopmanagementandroid.data.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.coffeeshopmanagementandroid.data.api.CategoryService;
import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.category.request.GetAllCategoriesRequest;
import com.example.coffeeshopmanagementandroid.data.dto.category.response.CategoryResponse;
import com.example.coffeeshopmanagementandroid.domain.repository.CategoryRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class CategoryRepositoryImpl implements CategoryRepository {
    CategoryService categoryService;
    public CategoryRepositoryImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @SuppressLint("LongLogTag")
    @Override
    public BasePagingResponse<List<CategoryResponse>> getAllCategories(GetAllCategoriesRequest request) throws Exception {
        Log.d("Category RepoIml - getAllCategories", "Called");
        Call<BasePagingResponse<List<CategoryResponse>>> call = categoryService.getAllCategories(request.getPage(), request.getLimit(), request.getSortType(), request.getSortBy());
        Response<BasePagingResponse<List<CategoryResponse>>> response = call.execute();
        if(response.isSuccessful() && response.body() != null) {
            Log.d("Response", String.valueOf(response.body().getData()));
            return response.body();
        } else {
            String errorMessage = "Get categories failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
            Log.e("GET CATEGORIES", errorMessage);
            throw new Exception(errorMessage);
        }
    }
}
