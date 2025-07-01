package com.example.coffeeshopmanagementandroid.data.dto.cart.response;

import java.math.BigDecimal;
import java.util.List;

public class CartResponse {
    private String id;
    private BigDecimal cartTotalCost;
    private BigDecimal cartDiscountCost;
    private BigDecimal cartTotalCostAfterDiscount;
    private List<String> usedDiscounts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getCartTotalCost() {
        return cartTotalCost;
    }

    public void setCartTotalCost(BigDecimal cartTotalCost) {
        this.cartTotalCost = cartTotalCost;
    }

    public BigDecimal getCartDiscountCost() {
        return cartDiscountCost;
    }

    public void setCartDiscountCost(BigDecimal cartDiscountCost) {
        this.cartDiscountCost = cartDiscountCost;
    }

    public BigDecimal getCartTotalCostAfterDiscount() {
        return cartTotalCostAfterDiscount;
    }

    public void setCartTotalCostAfterDiscount(BigDecimal cartTotalCostAfterDiscount) {
        this.cartTotalCostAfterDiscount = cartTotalCostAfterDiscount;
    }

    public List<String> getUsedDiscounts() {
        return usedDiscounts;
    }

    public void setUsedDiscounts(List<String> usedDiscounts) {
        this.usedDiscounts = usedDiscounts;
    }
}