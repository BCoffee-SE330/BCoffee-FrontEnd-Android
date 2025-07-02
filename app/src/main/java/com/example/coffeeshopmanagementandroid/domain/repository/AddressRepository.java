package com.example.coffeeshopmanagementandroid.domain.repository;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.address.response.AddressResponse;
import com.example.coffeeshopmanagementandroid.data.dto.address.resquest.CreateAddressRequest;
import com.example.coffeeshopmanagementandroid.data.dto.address.resquest.GetAddressRequest;
import com.example.coffeeshopmanagementandroid.data.dto.address.resquest.UpdateAddressRequest;

import java.util.List;
import java.util.UUID;

public interface AddressRepository {
    BasePagingResponse<List<AddressResponse>> getAddresses(GetAddressRequest request) throws Exception;
    BaseResponse<AddressResponse> createAddress(CreateAddressRequest request) throws Exception;
    BaseResponse<AddressResponse> updateAddress(UpdateAddressRequest request) throws Exception;
    void deleteAddress(UUID id) throws Exception;
}