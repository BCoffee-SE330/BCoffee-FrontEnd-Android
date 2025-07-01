package com.example.coffeeshopmanagementandroid.domain.model;

public class ProductCartModel {
    private String productId;
    private String productName;
    private double productPrice;
    private final String productVarient;
    private int quantity;

    public ProductCartModel(String productId, String productName, double productPrice, String productVarient, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productVarient = productVarient;
        this.quantity = quantity;
    }

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

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
    public String getProductVarient() {
        return productVarient;
    }



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
