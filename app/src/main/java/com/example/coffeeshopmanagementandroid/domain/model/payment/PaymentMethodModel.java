package com.example.coffeeshopmanagementandroid.domain.model.payment;

public class PaymentMethodModel {
    private String paymentMethodId;
    private String paymentMethodName;
    private String paymentMethodDescription;
    private Boolean active;

    public PaymentMethodModel(String paymentMethodId, String paymentMethodName, String paymentMethodDescription, Boolean active) {
        this.paymentMethodId = paymentMethodId;
        this.paymentMethodName = paymentMethodName;
        this.paymentMethodDescription = paymentMethodDescription;
        this.active = active;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public String getPaymentMethodDescription() {
        return paymentMethodDescription;
    }

    public Boolean getActive() {
        return active;
    }
}
