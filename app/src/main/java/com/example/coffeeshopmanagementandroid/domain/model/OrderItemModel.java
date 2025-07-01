package com.example.coffeeshopmanagementandroid.domain.model;

public class OrderItemModel {
    private String thumb;
    private String productName;
    private String variant;
    private int quantity;
    private double unitPrice;

    public OrderItemModel(String thumb, String productName, String variant, int quantity, double unitPrice) {
        this.thumb = thumb;
        this.productName = productName;
        this.variant = variant;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getThumb() { return thumb; }
    public void setThumb(String thumb) { this.thumb = thumb; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getVariant() { return this.variant; }
    public void setVariant(String variant) { this.variant = variant; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
}
