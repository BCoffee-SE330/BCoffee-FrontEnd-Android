package com.example.coffeeshopmanagementandroid.domain.model;

public class DiscountProductVariantsModel {
    private String discountId;
    private String variantId;

    public DiscountProductVariantsModel(String discountId, String variantId) {
        this.discountId = discountId;
        this.variantId = variantId;
    }

    // Getter và Setter cho discountId
    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    // Getter và Setter cho variantId
    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }
}
