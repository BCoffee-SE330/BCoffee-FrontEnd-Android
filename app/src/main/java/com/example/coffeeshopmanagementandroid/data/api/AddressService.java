package com.example.coffeeshopmanagementandroid.data.api;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.address.response.AddressResponse;
import com.example.coffeeshopmanagementandroid.data.dto.address.resquest.CreateAddressRequest;
import com.example.coffeeshopmanagementandroid.data.dto.address.resquest.UpdateAddressRequest;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AddressService {
    @GET("shipping-address/me")
    Call<BasePagingResponse<List<AddressResponse>>> getAddresses(@Query("page") int page,
                                                                 @Query("limit") int limit,
                                                                 @Query("sortType") String sortType,
                                                                 @Query("sortBy") String sortBy);

    @POST("shipping-address/")
    Call<BaseResponse<AddressResponse>> createAddress(@Body CreateAddressRequest request);

    @HTTP(method = "PATCH", path = "shipping-address/", hasBody = true)
    Call<BaseResponse<AddressResponse>> updateAddress(@Body UpdateAddressRequest request);

    @DELETE("shipping-address/{id}")
    Call<Void> deleteAddress(@Path("id") UUID id);
}