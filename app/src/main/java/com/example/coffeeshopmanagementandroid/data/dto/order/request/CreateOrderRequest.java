package com.example.coffeeshopmanagementandroid.data.dto.order.request;

public class CreateOrderRequest {
    private String shippingAddressId;
    private String paymentMethodId;
    private String branchId;

    public CreateOrderRequest(String shippingAddressId, String paymentMethodId, String branchId) {
        this.shippingAddressId = shippingAddressId;
        this.paymentMethodId = paymentMethodId;
        this.branchId = branchId;
    }

    public String getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(String shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }
}
