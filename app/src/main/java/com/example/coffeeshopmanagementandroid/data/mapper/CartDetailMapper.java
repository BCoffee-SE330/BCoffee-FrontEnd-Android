package com.example.coffeeshopmanagementandroid.data.mapper;

import com.example.coffeeshopmanagementandroid.data.dto.cart.response.CartDetailResponse;
import com.example.coffeeshopmanagementandroid.domain.model.cart.CartItemModel;

import java.util.ArrayList;
import java.util.List;

public class CartDetailMapper {
    // Method to map CartDetailResponse to CartModel
    public static CartItemModel mapToCartItemModel(CartDetailResponse response) {
        if (response == null) {
            return null;
        }
        CartItemModel cartItemModel = new CartItemModel();
        cartItemModel.setVariantId(response.getVariantId());
        cartItemModel.setCartDetailQuantity(response.getCartDetailQuantity());
        cartItemModel.setCartDetailUnitPrice(response.getCartDetailUnitPrice());
        cartItemModel.setCartDetailTotalPrice(response.getCartDetailTotalPrice());
        cartItemModel.setCartDetailDiscountCost(response.getCartDetailDiscountCost());
        cartItemModel.setCartDetailUnitPriceAfterDiscount(response.getCartDetailUnitPriceAfterDiscount());
        cartItemModel.setCartDetailTotalPriceAfterDiscount(response.getCartDetailTotalPriceAfterDiscount());
        cartItemModel.setProductThumb(response.getProductThumb());
        cartItemModel.setProductName(response.getProductName());
        cartItemModel.setVariantTierIdx(response.getVariantTierIdx());
        cartItemModel.setProductId(response.getProductId());
        return cartItemModel;
    }

    public static List<CartItemModel> mapToCartItemModels(List<CartDetailResponse> responses) {
        List<CartItemModel> cartItemModels = new ArrayList<>();
        for (CartDetailResponse response : responses) {
            CartItemModel model = mapToCartItemModel(response);
            if (model != null) {
                cartItemModels.add(model);
            }
        }
        return cartItemModels;
    }
}
