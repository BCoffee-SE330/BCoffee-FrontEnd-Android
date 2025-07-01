package com.example.coffeeshopmanagementandroid.domain.model.cart;


public class CartItemModel {
    private String variantId;
    private Integer cartDetailQuantity;
    private Double cartDetailUnitPrice;
    private Double cartDetailTotalPrice;
    private Double cartDetailDiscountCost;
    private Double cartDetailUnitPriceAfterDiscount;
    private Double cartDetailTotalPriceAfterDiscount;
    private String productId;
    private String productThumb;
    private String productName;
    private String variantTierIdx;

    public CartItemModel() {

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

    public Double getCartDetailUnitPrice() {
        return cartDetailUnitPrice;
    }

    public void setCartDetailUnitPrice(Double cartDetailUnitPrice) {
        this.cartDetailUnitPrice = cartDetailUnitPrice;
    }

    public Double getCartDetailTotalPrice() {
        return cartDetailTotalPrice;
    }

    public void setCartDetailTotalPrice(Double cartDetailTotalPrice) {
        this.cartDetailTotalPrice = cartDetailTotalPrice;
    }

    public Double getCartDetailDiscountCost() {
        return cartDetailDiscountCost;
    }

    public void setCartDetailDiscountCost(Double cartDetailDiscountCost) {
        this.cartDetailDiscountCost = cartDetailDiscountCost;
    }

    public Double getCartDetailUnitPriceAfterDiscount() {
        return cartDetailUnitPriceAfterDiscount;
    }

    public void setCartDetailUnitPriceAfterDiscount(Double cartDetailUnitPriceAfterDiscount) {
        this.cartDetailUnitPriceAfterDiscount = cartDetailUnitPriceAfterDiscount;
    }

    public Double getCartDetailTotalPriceAfterDiscount() {
        return cartDetailTotalPriceAfterDiscount;
    }

    public void setCartDetailTotalPriceAfterDiscount(Double cartDetailTotalPriceAfterDiscount) {
        this.cartDetailTotalPriceAfterDiscount = cartDetailTotalPriceAfterDiscount;
    }

    public String getProductThumb() {
        return productThumb;
    }

    public void setProductThumb(String productThumb) {
        this.productThumb = productThumb;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVariantTierIdx() {
        return variantTierIdx;
    }

    public void setVariantTierIdx(String variantTierIdx) {
        this.variantTierIdx = variantTierIdx;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
