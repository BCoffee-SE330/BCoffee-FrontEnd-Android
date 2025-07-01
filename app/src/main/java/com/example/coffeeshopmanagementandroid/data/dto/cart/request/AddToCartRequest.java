package com.example.coffeeshopmanagementandroid.data.dto.cart.request;

import com.google.gson.annotations.SerializedName;

public class AddToCartRequest {
    @SerializedName("variantId")
    private String variantId;
    @SerializedName("cartDetailQuantity")
    private Integer cartDetailQuantity;

    public AddToCartRequest(String variantId, Integer cartDetailQuantity) {
        this.variantId = variantId;
        this.cartDetailQuantity = cartDetailQuantity;
    }
    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public Integer getCartDetailQuantity() {
        return cartDetailQuantity;
    }

    public void setCartDetailQuantity(Integer cartDetailQuantity) {
        this.cartDetailQuantity = cartDetailQuantity;
    }
}
