package com.example.coffeeshopmanagementandroid.data.repository;

import android.util.Log;

import com.example.coffeeshopmanagementandroid.data.api.OrderService;
import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.order.request.CreateOrderRequest;
import com.example.coffeeshopmanagementandroid.data.dto.order.request.GetAllOrdersCustomerRequest;
import com.example.coffeeshopmanagementandroid.data.dto.order.response.GetDetailOrderResponse;
import com.example.coffeeshopmanagementandroid.data.dto.order.response.OrderResponse;
import com.example.coffeeshopmanagementandroid.domain.repository.OrderRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class OrderRepositoryImpl implements OrderRepository {
    private final OrderService orderService;
    public OrderRepositoryImpl(OrderService orderService) {
        this.orderService = orderService;
    }
    @Override
    public BasePagingResponse<List<OrderResponse>> getAllOrdersCustomer(GetAllOrdersCustomerRequest request) throws Exception {
        Log.d("Order RepoIml", "Called");
        Call<BasePagingResponse<List<OrderResponse>>> call = orderService.getAllOrdersCustomer(request.getPage(), request.getLimit(), request.getSortType().toString(), request.getSortBy().toString());
        Response<BasePagingResponse<List<OrderResponse>>> response = call.execute();

        if (response.isSuccessful() && response.body() != null) {
            Log.d("Response", String.valueOf(response.body().getData()));
            return response.body();
        } else {
            String errorMessage = "Get orders failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
            Log.e("GET ORDERS", errorMessage);
            throw new Exception(errorMessage);
        }
    }

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) throws Exception {
        try {
            Call<BaseResponse<OrderResponse>> call = orderService.createOrder(request);
            Response<BaseResponse<OrderResponse>> response = call.execute();
            if (response.isSuccessful()) {
                Log.d("UpdateCartItem", "Cart item updated successfully");
                return response.body().getData();
            } else {
                String errorMessage = "Update cart item failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
                Log.e("UPDATE CART ITEM", errorMessage);
                throw new Exception(errorMessage);
            }
        } catch (Exception e) {
            Log.e("UPDATE CART ITEM", "Exception: " + e.getMessage());
            return null;
        }
    }

    @Override
    public GetDetailOrderResponse getDetailOrder(String orderId) throws Exception {
        try {
            Call<BaseResponse<GetDetailOrderResponse>> call = orderService.getDetailOrder(orderId);
            Response<BaseResponse<GetDetailOrderResponse>> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                Log.d("GetDetailOrder", "Order details retrieved successfully");
                return response.body().getData();
            } else {
                String errorMessage = "Get order details failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
                Log.e("GET ORDER DETAILS", errorMessage);
                throw new Exception(errorMessage);
            }
        } catch (Exception e) {
            Log.e("GET ORDER DETAILS", "Exception: " + e.getMessage());
            throw new Exception("Failed to get order details", e);
        }
    }

    @Override
    public BaseResponse<Void> updateOrderStatus(String orderId, String status) throws Exception {
        try {
            Call<BaseResponse<Void>> call = orderService.updateOrderStatus(orderId, status);
            Response<BaseResponse<Void>> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                Log.d("UpdateOrderStatus", "Order status updated successfully");
            } else {
                String errorMessage = "Update order status failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
                Log.e("UPDATE ORDER STATUS", errorMessage);
                throw new Exception(errorMessage);
            }
        } catch (Exception e) {
            Log.e("UPDATE ORDER STATUS", "Exception: " + e.getMessage());
            throw new Exception("Failed to update order status", e);
        }

        return null;
    }
}
