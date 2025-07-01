package com.example.coffeeshopmanagementandroid.data.dto.order.response;

import com.example.coffeeshopmanagementandroid.domain.model.cart.CartItemModel;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class OrderDetailResponse {
    private String id;
    private String createdAt;
    private String updatedAt;
    private int orderDetailQuantity;
    private BigDecimal orderDetailUnitPrice;
    private String productVariantId;
    private String orderId;
    private String productName;
    private String productThumb;
    private String variantTierId;
    public OrderDetailResponse() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrderDetailQuantity() {
        return orderDetailQuantity;
    }

    public void setOrderDetailQuantity(int orderDetailQuantity) {
        this.orderDetailQuantity = orderDetailQuantity;
    }

    public BigDecimal getOrderDetailUnitPrice() {
        return orderDetailUnitPrice;
    }

    public void setOrderDetailUnitPrice(BigDecimal orderDetailUnitPrice) {
        this.orderDetailUnitPrice = orderDetailUnitPrice;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


    public String getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(String productVariantId) {
        this.productVariantId = productVariantId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductThumb() {
        return productThumb;
    }

    public void setProductThumb(String productThumb) {
        this.productThumb = productThumb;
    }

    public String getVariantTierId() {
        return variantTierId;
    }

    public void setVariantTierId(String variantTierId) {
        this.variantTierId = variantTierId;
    }
}
