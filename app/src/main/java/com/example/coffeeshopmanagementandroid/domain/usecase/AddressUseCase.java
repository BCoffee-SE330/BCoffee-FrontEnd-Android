package com.example.coffeeshopmanagementandroid.domain.usecase;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.address.response.AddressResponse;
import com.example.coffeeshopmanagementandroid.data.dto.address.resquest.CreateAddressRequest;
import com.example.coffeeshopmanagementandroid.data.dto.address.resquest.GetAddressRequest;
import com.example.coffeeshopmanagementandroid.data.dto.address.resquest.UpdateAddressRequest;
import com.example.coffeeshopmanagementandroid.domain.repository.AddressRepository;

import java.util.List;
import java.util.UUID;

public class AddressUseCase {
    private final AddressRepository addressRepository;
    public AddressUseCase(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public BasePagingResponse<List<AddressResponse>> getAddresses(GetAddressRequest request) {
        // Logic to get all cart items
        try {
            return addressRepository.getAddresses(request);
        } catch (Exception e) {
            // Handle exceptions, possibly rethrow or log
            throw new RuntimeException("Failed to retrieve cart items", e);
        }
    }

    public BaseResponse<AddressResponse> createAddress(CreateAddressRequest addressRequest) {
        try {
            return addressRepository.createAddress(addressRequest);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create address", e);
        }
    }

    public BaseResponse<AddressResponse> updateAddress(UpdateAddressRequest request) {
        try {
            return addressRepository.updateAddress(request);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update address", e);
        }
    }

    public void deleteAddress(UUID id) {
        try {
            addressRepository.deleteAddress(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete address", e);
        }
    }
}
