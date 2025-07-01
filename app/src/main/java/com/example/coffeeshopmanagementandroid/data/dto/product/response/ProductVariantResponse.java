package com.example.coffeeshopmanagementandroid.data.dto.product.response;

public class ProductVariantResponse {
    private String id;
    private String variantTierIdx;
    private Boolean variantDefault;
    private String variantSlug;
    private Integer variantSort;
    private Integer variantPrice;
    private String productId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVariantTierIdx() {
        return variantTierIdx;
    }

    public void setVariantTierIdx(String variantTierIdx) {
        this.variantTierIdx = variantTierIdx;
    }

    public Boolean getVariantDefault() {
        return variantDefault;
    }

    public void setVariantDefault(Boolean variantDefault) {
        this.variantDefault = variantDefault;
    }

    public String getVariantSlug() {
        return variantSlug;
    }

    public void setVariantSlug(String variantSlug) {
        this.variantSlug = variantSlug;
    }

    public Integer getVariantSort() {
        return variantSort;
    }

    public void setVariantSort(Integer variantSort) {
        this.variantSort = variantSort;
    }

    public Integer getVariantPrice() {
        return variantPrice;
    }

    public void setVariantPrice(Integer variantPrice) {
        this.variantPrice = variantPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
