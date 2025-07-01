package com.example.coffeeshopmanagementandroid.utils.enums;

public enum PaymentMethod {
    ZALO_PAY("Zalo Pay"),
    MOMO("Momo"),
    VN_PAY("VN PAY"),
    PAYPAL("Paypal"),
    CASH("Tiền mặt");

    private final String value;

    PaymentMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static PaymentMethod fromValue(String value) {
        for (PaymentMethod method : PaymentMethod.values()) {
            if (method.value.equalsIgnoreCase(value)) {
                return method;
            }
        }
        throw new IllegalArgumentException("Unknown payment method: " + value);
    }
}
