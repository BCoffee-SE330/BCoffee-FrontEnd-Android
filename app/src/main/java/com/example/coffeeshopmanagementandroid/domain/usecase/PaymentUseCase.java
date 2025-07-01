package com.example.coffeeshopmanagementandroid.domain.usecase;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.cart.response.CartDetailResponse;
import com.example.coffeeshopmanagementandroid.data.dto.payment.request.GetAllPaymentRequest;
import com.example.coffeeshopmanagementandroid.data.dto.payment.response.PaymentResponse;
import com.example.coffeeshopmanagementandroid.domain.repository.CartRepository;
import com.example.coffeeshopmanagementandroid.domain.repository.PaymentRepository;

import java.util.List;

public class PaymentUseCase {
    private final PaymentRepository paymentRepository;

    public PaymentUseCase(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public BasePagingResponse<List<PaymentResponse>> getPaymentMethods(GetAllPaymentRequest request) throws Exception {
        try {
            return paymentRepository.getPaymentMethods(request);
        } catch (Exception e) {
            // Handle exceptions, possibly rethrow or log
            throw new RuntimeException("Failed to retrieve payment methods", e);
        }
    }
}
