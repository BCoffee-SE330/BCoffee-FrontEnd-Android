package com.example.coffeeshopmanagementandroid.data.dto.product.response;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class ProductResponse {
    @SerializedName("id")
    private String productId;
    private String productName;
    private String productThumb;
    private String productDescription;
    private BigDecimal productPrice;
    private String productSlug;
    private BigDecimal productRatingsAverage;
    private Boolean productIsPublished;
    private Boolean productIsDeleted;
    private String productCategoryId;
    @SerializedName("favorite")
    private boolean isFavorite;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductSlug() {
        return productSlug;
    }

    public void setProductSlug(String productSlug) {
        this.productSlug = productSlug;
    }

    public BigDecimal getProductRatingsAverage() {
        return productRatingsAverage;
    }

    public void setProductRatingsAverage(BigDecimal productRatingsAverage) {
        this.productRatingsAverage = productRatingsAverage;
    }

    public Boolean getProductIsPublished() {
        return productIsPublished;
    }

    public void setProductIsPublished(Boolean productIsPublished) {
        this.productIsPublished = productIsPublished;
    }

    public Boolean getProductIsDeleted() {
        return productIsDeleted;
    }

    public void setProductIsDeleted(Boolean productIsDeleted) {
        this.productIsDeleted = productIsDeleted;
    }

    public String getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(String productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
}
