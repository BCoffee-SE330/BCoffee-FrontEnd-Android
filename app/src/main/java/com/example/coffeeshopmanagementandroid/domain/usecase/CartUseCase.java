package com.example.coffeeshopmanagementandroid.domain.usecase;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.cart.request.AddToCartRequest;
import com.example.coffeeshopmanagementandroid.data.dto.cart.request.GetAllCartItemRequest;
import com.example.coffeeshopmanagementandroid.data.dto.cart.request.UpdateCartRequest;
import com.example.coffeeshopmanagementandroid.data.dto.cart.response.CartDetailResponse;
import com.example.coffeeshopmanagementandroid.data.dto.cart.response.CartResponse;
import com.example.coffeeshopmanagementandroid.domain.repository.CartRepository;

import java.util.List;

public class CartUseCase {
    private final CartRepository cartRepository;
    public CartUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
    public String addToCart(AddToCartRequest request) {
        // Logic to add item to cart
        if (request == null || request.getVariantId() == null || request.getCartDetailQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid request data");
        }
        try {
            return cartRepository.addToCart(request);
        } catch (Exception e) {
            // Handle exceptions, possibly rethrow or log
            throw new RuntimeException("Failed to add item to cart", e);

        }
    }

    public BasePagingResponse<List<CartDetailResponse>> getCartItems(GetAllCartItemRequest request) {
        // Logic to get all cart items
        try {
            return cartRepository.getCartItem(request);
        } catch (Exception e) {
            // Handle exceptions, possibly rethrow or log
            throw new RuntimeException("Failed to retrieve cart items", e);
        }
    }

    public String updateCartItem(UpdateCartRequest request) {
        if (request == null || request.getVariantId() == null || request.getCartDetailQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid request data");
        }
        try {
            cartRepository.updateCartItem(request);
            return "Cart item updated successfully";
        } catch (Exception e) {
            // Handle exceptions, possibly rethrow or log
            throw new RuntimeException("Failed to add item to cart", e);

        }
    }

    public String deleteCartItem(String variantId) {
        if (variantId == null || variantId.isEmpty()) {
            throw new IllegalArgumentException("Invalid variant ID");
        }
        try {
            cartRepository.deleteCartitem(variantId);
            return "Cart item deleted successfully";
        } catch (Exception e) {
            // Handle exceptions, possibly rethrow or log
            throw new RuntimeException("Failed to delete cart item", e);
        }
    }

    public BaseResponse<CartResponse> applyDiscountToCart(String branchId) {
        if (branchId == null || branchId.isEmpty()) {
            throw new IllegalArgumentException("Invalid branch ID");
        }
        try {
            return cartRepository.applyDiscountToCart(branchId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to apply discount to cart", e);
        }
    }

}
