package com.example.coffeeshopmanagementandroid.data.api;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.discount.response.DiscountResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DiscountService {
    @POST("/discount/filter-by-ids")
    Call<BasePagingResponse<List<DiscountResponse>>> findDiscountsByIds(
            @Body List<String> discountIds,
            @Query("page") int page,
            @Query("limit") int limit,
            @Query("sortType") String sortType,
            @Query("sortBy") String sortBy
    );

    @GET("/discount/active-not-expired")
    Call<BasePagingResponse<List<DiscountResponse>>> findAllDiscounts(
            @Query("page") int page,
            @Query("limit") int limit,
            @Query("sortType") String sortType,
            @Query("sortBy") String sortBy
    );

    @GET("/discount/{id}")
    Call<BaseResponse<DiscountResponse>> findDiscountById(
            @Path("id") String id
    );
}