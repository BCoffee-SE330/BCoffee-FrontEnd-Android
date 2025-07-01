package com.example.coffeeshopmanagementandroid.domain.repository;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.cart.request.GetAllCartItemRequest;
import com.example.coffeeshopmanagementandroid.data.dto.cart.response.CartDetailResponse;
import com.example.coffeeshopmanagementandroid.data.dto.payment.request.GetAllPaymentRequest;
import com.example.coffeeshopmanagementandroid.data.dto.payment.response.PaymentResponse;

import java.util.List;

public interface PaymentRepository {
    BasePagingResponse<List<PaymentResponse>> getPaymentMethods(GetAllPaymentRequest request) throws Exception;
}
