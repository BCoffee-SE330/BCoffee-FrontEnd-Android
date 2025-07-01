package com.example.coffeeshopmanagementandroid.data.dto.address.response;

import com.google.gson.annotations.SerializedName;

public class AddressResponse {
    @SerializedName("id")
    private String addressId;
    private String userId;
    private String addressLine;
    private String addressCity;
    private String addressDistrict;
    private boolean addressIsDefault;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
