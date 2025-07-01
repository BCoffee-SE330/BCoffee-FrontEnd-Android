package com.example.coffeeshopmanagementandroid.data.dto.address.resquest;

public class CreateAddressRequest {
    private String addressLine;
    private String addressCity;
    private String addressDistrict;
    private boolean addressIsDefault;

    public CreateAddressRequest() {}

    public CreateAddressRequest(String addressLine, String addressCity, String addressDistrict, boolean addressIsDefault) {
        this.addressLine = addressLine;
        this.addressCity = addressCity;
        this.addressDistrict = addressDistrict;
        this.addressIsDefault = addressIsDefault;
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