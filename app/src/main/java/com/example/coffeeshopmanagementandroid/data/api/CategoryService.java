package com.example.coffeeshopmanagementandroid.data.api;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.category.response.CategoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CategoryService {
    @GET("product-category/all")
    Call<BasePagingResponse<List<CategoryResponse>>> getAllCategories(@Query("page") int page,
                                                                      @Query("limit") int limit,
                                                                      @Query("sortType") String sortType,
                                                                      @Query("sortBy") String sortBy);
}
