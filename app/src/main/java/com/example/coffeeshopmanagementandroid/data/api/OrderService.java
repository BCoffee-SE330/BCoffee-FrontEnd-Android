package com.example.coffeeshopmanagementandroid.data.api;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.order.request.CreateOrderRequest;
import com.example.coffeeshopmanagementandroid.data.dto.order.response.GetDetailOrderResponse;
import com.example.coffeeshopmanagementandroid.data.dto.order.response.OrderResponse;
import com.example.coffeeshopmanagementandroid.data.dto.product.response.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrderService {
    @GET("/orders/customer")
    Call<BasePagingResponse<List<OrderResponse>>> getAllOrdersCustomer(@Query("page") int page,
                                                                       @Query("limit") int limit,
                                                                       @Query("sortType") String sortType,
                                                                       @Query("sortBy") String sortBy
    );

    @POST("/orders/")
    Call<BaseResponse<OrderResponse>> createOrder(@Body CreateOrderRequest createOrderRequest
    );

    @GET("/orders/me/{orderId}")
    Call<BaseResponse<GetDetailOrderResponse>> getDetailOrder(@Path("orderId") String orderId);

    @PUT("/orders/{id}/status")
    Call<BaseResponse<Void>> updateOrderStatus(
            @Path("id") String orderId,
            @Query("status") String status
    );
}
