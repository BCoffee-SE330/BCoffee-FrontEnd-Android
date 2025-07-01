package com.example.coffeeshopmanagementandroid.data.mapper;

import com.example.coffeeshopmanagementandroid.data.dto.payment.response.PaymentResponse;
import com.example.coffeeshopmanagementandroid.domain.model.payment.PaymentMethodModel;

import java.util.ArrayList;
import java.util.List;

public class PaymentMapper {
    public static PaymentMethodModel mapToPaymentModel(PaymentResponse response) {
        if (response == null) {
            return null;
        }
        return new PaymentMethodModel(
                response.getPaymentMethodId(),
                response.getPaymentMethodName(),
                response.getPaymentMethodDescription(),
                response.getActive()
        );
    }
    public static List<PaymentMethodModel> mapToPaymentModels(List<PaymentResponse> responses) {
        List<PaymentMethodModel> items = new ArrayList<>();
        for (PaymentResponse response : responses) {
            PaymentMethodModel model = mapToPaymentModel(response);
            if (model != null) {
                items.add(model);
            }
        }
        return items;
    }
}
