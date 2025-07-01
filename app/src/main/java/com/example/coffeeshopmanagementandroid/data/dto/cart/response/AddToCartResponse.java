package com.example.coffeeshopmanagementandroid.data.dto.cart.response;

public class AddToCartResponse {
    private String id;
    private String customerId;
    private Double cartTotalCost;
    private Double cartDiscountCost;
    private Double cartTotalCostAfterDiscount;

    public AddToCartResponse(String id, String customerId, Double cartTotalCost, Double cartDiscountCost, Double cartTotalCostAfterDiscount) {
        this.id = id;
        this.customerId = customerId;
        this.cartTotalCost = cartTotalCost;
        this.cartDiscountCost = cartDiscountCost;
        this.cartTotalCostAfterDiscount = cartTotalCostAfterDiscount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Double getCartTotalCost() {
        return cartTotalCost;
    }

    public void setCartTotalCost(Double cartTotalCost) {
        this.cartTotalCost = cartTotalCost;
    }

    public Double getCartDiscountCost() {
        return cartDiscountCost;
    }

    public void setCartDiscountCost(Double cartDiscountCost) {
        this.cartDiscountCost = cartDiscountCost;
    }

    public Double getCartTotalCostAfterDiscount() {
        return cartTotalCostAfterDiscount;
    }

    public void setCartTotalCostAfterDiscount(Double cartTotalCostAfterDiscount) {
        this.cartTotalCostAfterDiscount = cartTotalCostAfterDiscount;
    }
}
