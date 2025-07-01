package com.example.coffeeshopmanagementandroid.domain.repository;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.order.request.CreateOrderRequest;
import com.example.coffeeshopmanagementandroid.data.dto.order.request.GetAllOrdersCustomerRequest;
import com.example.coffeeshopmanagementandroid.data.dto.order.response.GetDetailOrderResponse;
import com.example.coffeeshopmanagementandroid.data.dto.order.response.OrderResponse;

import java.util.List;

public interface OrderRepository {
    BasePagingResponse<List<OrderResponse>> getAllOrdersCustomer(GetAllOrdersCustomerRequest request) throws Exception;
    OrderResponse createOrder(CreateOrderRequest request) throws Exception;

    GetDetailOrderResponse getDetailOrder(String orderId) throws Exception;

    BaseResponse<Void> updateOrderStatus(String orderId, String status) throws Exception;
}
