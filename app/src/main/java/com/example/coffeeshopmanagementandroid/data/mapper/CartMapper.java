package com.example.coffeeshopmanagementandroid.data.mapper;

import com.example.coffeeshopmanagementandroid.data.dto.cart.response.CartResponse;
import com.example.coffeeshopmanagementandroid.domain.model.cart.CartModel;

public class CartMapper {
    public static CartModel mapToCartModel(CartResponse response) {
        if (response == null) {
            return null;
        }
        CartModel cartModel = new CartModel();
        cartModel.setId(response.getId());
        cartModel.setCartTotalCost(response.getCartTotalCost());
        cartModel.setCartDiscountCost(response.getCartDiscountCost());
        cartModel.setCartTotalCostAfterDiscount(response.getCartTotalCostAfterDiscount());
        cartModel.setUsedDiscounts(response.getUsedDiscounts());
        return cartModel;
    }
}