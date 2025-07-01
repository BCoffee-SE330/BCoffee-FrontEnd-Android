package com.example.coffeeshopmanagementandroid.data.api;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.product.response.ProductResponse;
import com.example.coffeeshopmanagementandroid.data.dto.product.response.ProductVariantResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductService {
    @GET("/product/all")
    Call<BasePagingResponse<List<ProductResponse>>> getAllProducts(@Query("page") int page,
                                                                   @Query("limit") int limit,
                                                                   @Query("sortType") String sortType,
                                                                   @Query("sortBy") String sortBy
    );

    @GET("/product/user/{id}")
    Call<BaseResponse<ProductResponse>> getProductById(@Path("id") String id);

    @GET("/product-variant/by-product/{productId}")
    Call<BasePagingResponse<List<ProductVariantResponse>>> getAllProductVariants(@Path("productId") String productId,
                                                                                 @Query("page") int page,
                                                                                 @Query("limit") int limit,
                                                                                 @Query("sortType") String sortType,
                                                                                 @Query("sortBy") String sortBy);
    @GET("/favorite-drinks/user")
    Call<BasePagingResponse<List<ProductResponse>>> getAllFavoriteProducts(@Query("page") int page,
                                                                  @Query("limit") int limit,
                                                                  @Query("sortType") String sortType,
                                                                  @Query("sortBy") String sortBy
    );

    @POST("/favorite-drinks/{drinkId}")
    Call<BaseResponse<Void>> addProductToFavorite(@Path("drinkId") String drinkId);

    @GET("/product/best-selling")
    Call<BasePagingResponse<List<ProductResponse>>> getBestSellingProducts(@Query("page") int page,
                                                                           @Query("limit") int limit,
                                                                           @Query("sortType") String sortType,
                                                                           @Query("sortBy") String sortBy
    );
}
