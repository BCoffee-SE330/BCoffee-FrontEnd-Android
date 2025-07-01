package com.example.coffeeshopmanagementandroid.data.api;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.cart.request.AddToCartRequest;
import com.example.coffeeshopmanagementandroid.data.dto.cart.request.UpdateCartRequest;
import com.example.coffeeshopmanagementandroid.data.dto.cart.response.AddToCartResponse;
import com.example.coffeeshopmanagementandroid.data.dto.cart.response.CartDetailResponse;
import com.example.coffeeshopmanagementandroid.data.dto.cart.response.CartResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CartService {
    @POST("cart/detail")
    Call<BaseResponse<AddToCartResponse>> addToCart(@Body AddToCartRequest request);

    @GET("cart/details")
    Call<BasePagingResponse<List<CartDetailResponse>>> getCartDetails(@Query("page") int page,
                                                                      @Query("limit") int limit,
                                                                      @Query("sortType") String sortType,
                                                                      @Query("sortBy") String sortBy);

    @PUT("cart/detail")
    Call<Void> updateCartItem(@Body UpdateCartRequest request);
    @DELETE("cart/detail/{variantId}")
    Call<Void> deleteCartItem(@Path("variantId") String variantId);

    @GET("discount/apply-to-cart")
    Call<BaseResponse<CartResponse>> applyDiscountToCart(@Query("branchId") String branchId);
}
