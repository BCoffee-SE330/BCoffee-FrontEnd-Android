package com.example.coffeeshopmanagementandroid.data.dto.cart.request;

public class UpdateCartRequest {
    private String variantId;
    private Integer cartDetailQuantity;

    public UpdateCartRequest(String variantId, Integer cartDetailQuantity) {
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
