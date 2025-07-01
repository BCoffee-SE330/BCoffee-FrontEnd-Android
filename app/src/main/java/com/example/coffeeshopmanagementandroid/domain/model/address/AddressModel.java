package com.example.coffeeshopmanagementandroid.domain.model.address;

public class AddressModel {
    private String addressId;
    private String addressLine;
    private String addressDistrict;
    private String addressCity;
    private boolean addressIsDefault;

    public AddressModel(String addressId, String addressLine, String addressDistrict, String addressCity, boolean addressIsDefault) {
        this.addressId = addressId;
        this.addressLine = addressLine;
        this.addressDistrict = addressDistrict;
        this.addressCity = addressCity;
        this.addressIsDefault = addressIsDefault;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressDistrict(String addressDistrict) {
        this.addressDistrict = addressDistrict;
    }

    public String getAddressDistrict() {
        return addressDistrict;
    }

    public void setAddressIsDefault(boolean addressIsDefault) {
        this.addressIsDefault = addressIsDefault;
    }

    public boolean getAddressIsDefault() {
        return addressIsDefault;
    }
}
