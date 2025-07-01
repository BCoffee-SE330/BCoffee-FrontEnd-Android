package com.example.coffeeshopmanagementandroid.data.api;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.payment.response.PaymentResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PaymentService {
    @GET("payment/payment-method/all")
    Call<BasePagingResponse<List<PaymentResponse>>> getAllPaymentMethods(@Query("page") int page,
                                                                        @Query("limit") int limit,
                                                                        @Query("sortType") String sortType,
                                                                        @Query("sortBy") String sortBy);
}
