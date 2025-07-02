package com.example.coffeeshopmanagementandroid.data.dto.address.resquest;

import java.util.UUID;

public class UpdateAddressRequest {
    private UUID shippingAddressId;
    private String addressLine;
    private String addressCity;
    private String addressDistrict;
    private boolean addressIsDefault;

    // Constructors
    public UpdateAddressRequest() {
    }

    public UpdateAddressRequest(UUID shippingAddressId, String addressLine, String addressCity,
                                String addressDistrict, boolean addressIsDefault) {
        this.shippingAddressId = shippingAddressId;
        this.addressLine = addressLine;
        this.addressCity = addressCity;
        this.addressDistrict = addressDistrict;
        this.addressIsDefault = addressIsDefault;
    }

    // Getters and setters
    public UUID getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(UUID shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressDistrict() {
        return addressDistrict;
    }

    public void setAddressDistrict(String addressDistrict) {
        this.addressDistrict = addressDistrict;
    }

    public boolean isAddressIsDefault() {
        return addressIsDefault;
    }

    public void setAddressIsDefault(boolean addressIsDefault) {
        this.addressIsDefault = addressIsDefault;
    }
}