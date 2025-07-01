package com.example.coffeeshopmanagementandroid.data.repository;

import android.util.Log;

import com.example.coffeeshopmanagementandroid.data.api.AddressService;
import com.example.coffeeshopmanagementandroid.data.api.CartService;
import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.address.response.AddressResponse;
import com.example.coffeeshopmanagementandroid.data.dto.address.resquest.CreateAddressRequest;
import com.example.coffeeshopmanagementandroid.data.dto.address.resquest.GetAddressRequest;
import com.example.coffeeshopmanagementandroid.data.dto.cart.response.CartDetailResponse;
import com.example.coffeeshopmanagementandroid.domain.repository.AddressRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AddressRepositoryImpl implements AddressRepository {
    private AddressService addressService;

    public AddressRepositoryImpl(AddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public BasePagingResponse<List<AddressResponse>> getAddresses(GetAddressRequest request) throws Exception {
        Log.d("Address RepoIml", "Called");

        Call<BasePagingResponse<List<AddressResponse>>> call = addressService.getAddresses(request.getPage(), request.getLimit(), request.getSortType().toString(), request.getSortBy().toString());
        Response<BasePagingResponse<List<AddressResponse>>> response = call.execute();

        if (response.isSuccessful() && response.body() != null) {
            Log.d("Response", String.valueOf(response.body().getData()));
            return response.body();
        } else {
            String errorMessage = "Get products failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
            Log.e("GET PRODUCTS", errorMessage);
            throw new Exception(errorMessage);
        }
    }

    @Override
    public BaseResponse<AddressResponse> createAddress(CreateAddressRequest request) throws Exception {
        Log.d("Address RepoIml", "Create Address Called");

        Call<BaseResponse<AddressResponse>> call = addressService.createAddress(request);
        Response<BaseResponse<AddressResponse>> response = call.execute();

        if (response.isSuccessful() && response.body() != null) {
            Log.d("Create Address Response", String.valueOf(response.body().getData()));
            return response.body();
        } else {
            String errorMessage = "Create address failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
            Log.e("CREATE ADDRESS", errorMessage);
            throw new Exception(errorMessage);
        }
    }
}
