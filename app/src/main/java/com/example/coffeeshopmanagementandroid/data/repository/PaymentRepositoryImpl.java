package com.example.coffeeshopmanagementandroid.data.repository;

import android.util.Log;

import com.example.coffeeshopmanagementandroid.data.api.PaymentService;
import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.cart.response.CartDetailResponse;
import com.example.coffeeshopmanagementandroid.data.dto.payment.request.GetAllPaymentRequest;
import com.example.coffeeshopmanagementandroid.data.dto.payment.response.PaymentResponse;
import com.example.coffeeshopmanagementandroid.domain.repository.PaymentRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PaymentRepositoryImpl implements PaymentRepository {
    private PaymentService paymentService;

    public PaymentRepositoryImpl(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public BasePagingResponse<List<PaymentResponse>> getPaymentMethods(GetAllPaymentRequest request) throws Exception {
        Call<BasePagingResponse<List<PaymentResponse>>> call = paymentService.getAllPaymentMethods(request.getPage(), request.getLimit(), request.getSortType().toString(), request.getSortBy().toString());
        Response<BasePagingResponse<List<PaymentResponse>>> response = call.execute();

        if (response.isSuccessful() && response.body() != null) {
            Log.d("Response", String.valueOf(response.body().getData()));
            return response.body();
        } else {
            String errorMessage = "Get products failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
            Log.e("GET PRODUCTS", errorMessage);
            throw new Exception(errorMessage);
        }
    }
}
