package com.example.coffeeshopmanagementandroid.data.repository;

import android.util.Log;

import com.example.coffeeshopmanagementandroid.data.api.CartService;
import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.cart.request.AddToCartRequest;
import com.example.coffeeshopmanagementandroid.data.dto.cart.request.GetAllCartItemRequest;
import com.example.coffeeshopmanagementandroid.data.dto.cart.request.UpdateCartRequest;
import com.example.coffeeshopmanagementandroid.data.dto.cart.response.AddToCartResponse;
import com.example.coffeeshopmanagementandroid.data.dto.cart.response.CartDetailResponse;
import com.example.coffeeshopmanagementandroid.data.dto.cart.response.CartResponse;
import com.example.coffeeshopmanagementandroid.domain.repository.CartRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class CartRepositoryImpl implements CartRepository {
    private CartService cartService;
    public CartRepositoryImpl(CartService cartService) {
        this.cartService = cartService;
    }
    @Override
    public String addToCart(AddToCartRequest request) {
        Call<BaseResponse<AddToCartResponse>> call = cartService.addToCart(request);
        try {
            Response<BaseResponse<AddToCartResponse>> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                Log.d("AddToCart", "Response: " + response.body());
                return response.body().getMessage();
            } else {
                String errorMessage = "Add to cart failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
                Log.e("ADD TO CART", errorMessage);
                return errorMessage;
            }
        } catch (Exception e) {
            Log.e("ADD TO CART", "Exception: " + e.getMessage());
            return "Error adding item to cart";
        }
    }

    @Override
    public BasePagingResponse<List<CartDetailResponse>> getCartItem(GetAllCartItemRequest request) throws Exception {
        Log.d("Product RepoIml", "Called");

        Call<BasePagingResponse<List<CartDetailResponse>>> call = cartService.getCartDetails(request.getPage(), request.getLimit(), request.getSortType().toString(), request.getSortBy().toString());
        Response<BasePagingResponse<List<CartDetailResponse>>> response = call.execute();

        if (response.isSuccessful() && response.body() != null) {
            Log.d("Response", String.valueOf(response.body().getData()));
            return response.body();
        } else {
            String errorMessage = "Get products failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
            Log.e("GET PRODUCTS", errorMessage);
            throw new Exception(errorMessage);
        }
    }

    @Override
    public Void updateCartItem(UpdateCartRequest request) {
        try {
            Call<Void> call = cartService.updateCartItem(request);
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                Log.d("UpdateCartItem", "Cart item updated successfully");
                return null;
            } else {
                String errorMessage = "Update cart item failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
                Log.e("UPDATE CART ITEM", errorMessage);
                throw new Exception(errorMessage);
            }
        } catch (Exception e) {
            Log.e("UPDATE CART ITEM", "Exception: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Void deleteCartitem(String variantId) throws Exception {
        try {
            Call<Void> call = cartService.deleteCartItem(variantId);
            Response<Void> response = call.execute();
            if(response.isSuccessful()) {
                Log.d("DeleteCartItem", "Cart item deleted successfully");
                return null;
            } else {
                String errorMessage = "Delete cart item failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
                Log.e("DELETE CART ITEM", errorMessage);
                throw new Exception(errorMessage);
            }
        } catch (Exception e) {
            Log.e("DELETE CART ITEM", e.getMessage());
            return null;
        }
    }

    @Override
    public BaseResponse<CartResponse> applyDiscountToCart(String branchId) throws Exception {
        try {
            Call<BaseResponse<CartResponse>> call = cartService.applyDiscountToCart(branchId);
            Response<BaseResponse<CartResponse>> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                Log.d("ApplyDiscount", "Response: " + response.body());
                return response.body();
            } else {
                String errorMessage = "Apply discount failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
                Log.e("APPLY DISCOUNT", errorMessage);
                throw new Exception(errorMessage);
            }
        } catch (Exception e) {
            Log.e("APPLY DISCOUNT", "Exception: " + e.getMessage());
            return null;
        }
    }
}
