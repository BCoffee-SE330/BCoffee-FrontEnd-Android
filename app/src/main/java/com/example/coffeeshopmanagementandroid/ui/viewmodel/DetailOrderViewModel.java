package com.example.coffeeshopmanagementandroid.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffeeshopmanagementandroid.data.dto.order.response.GetDetailOrderResponse;
import com.example.coffeeshopmanagementandroid.data.dto.order.response.OrderDetailResponse;
import com.example.coffeeshopmanagementandroid.domain.usecase.OrderUseCase;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DetailOrderViewModel extends ViewModel {
    private final OrderUseCase orderUseCase;
    private final MutableLiveData<String> address = new MutableLiveData<>();
    private final MutableLiveData<String> userName = new MutableLiveData<>();
    private final MutableLiveData<String> userPhoneNumber = new MutableLiveData<>();
    private final MutableLiveData<String> orderStatus = new MutableLiveData<>();
    private final MutableLiveData<BigDecimal> orderTotalCost = new MutableLiveData<>();
    private final MutableLiveData<BigDecimal> orderDiscountCost = new MutableLiveData<>();
    private final MutableLiveData<List<OrderDetailResponse>> orderItems = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    private final MutableLiveData<BigDecimal> totalPrice = new MutableLiveData<>();
    private final MutableLiveData<String> approvalLinkLiveData = new MutableLiveData<>();
    public MutableLiveData<String> getApprovalLinkLiveData() {
        return approvalLinkLiveData;
    }

    public OrderUseCase getOrderUseCase() {
        return orderUseCase;
    }

    public MutableLiveData<String> getAddress() {
        return address;
    }

    public MutableLiveData<String> getUserName() {
        return userName;
    }

    public MutableLiveData<String> getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public MutableLiveData<String> getOrderStatus() {
        return orderStatus;
    }

    public MutableLiveData<BigDecimal> getTotalPrice() {
        return totalPrice;
    }

    public MutableLiveData<List<OrderDetailResponse>> getOrderItems() {
        return orderItems;
    }

    public void setIsLoading(Boolean loading) {
        isLoading.postValue(loading);
    }

    public void setErrorLiveData(String errorMessage) {
        errorLiveData.postValue(errorMessage);
    }

    public MutableLiveData<BigDecimal> getOrderTotalCost() {
        return orderTotalCost;
    }

    public MutableLiveData<BigDecimal> getOrderDiscountCost() {
        return orderDiscountCost;
    }

    @Inject
    public DetailOrderViewModel(OrderUseCase orderUseCase) {
        this.orderUseCase = orderUseCase;
    }

    public void fetchOrderDetail(String orderId) {
        setIsLoading(true);
        new Thread(() -> {
            try {
                GetDetailOrderResponse orderDetail = orderUseCase.getDetailOrder(orderId);
                address.postValue(orderDetail.getShippingAddressName());
                userName.postValue(orderDetail.getUserName());
                userPhoneNumber.postValue(orderDetail.getUserPhoneNumber());
                orderStatus.postValue(orderDetail.getOrderStatus());

                // Set order items
                orderItems.postValue(orderDetail.getOrderDetails());

                // Set pricing information
                orderTotalCost.postValue(orderDetail.getOrderTotalCost());
                orderDiscountCost.postValue(orderDetail.getOrderDiscountCost());
                totalPrice.postValue(orderDetail.getOrderTotalCostAfterDiscount());

                if (orderDetail.getApprovalLink() != null) {
                    approvalLinkLiveData.postValue(orderDetail.getApprovalLink());
                }

            } catch (Exception e) {
                setErrorLiveData(e.getMessage());
                Log.e("DetailOrderViewModel", "Failed to fetch order details: " + e.getMessage(), e);
            } finally {
                setIsLoading(false);
            }
        }).start();
    }

    public void cancelOrder(String orderId) {
        new Thread(() -> {
            try {
                orderUseCase.updateOrderStatus(orderId, "CANCELLED");
                Log.d("OrderViewModel", "Order cancelled successfully: " + orderId);
            } catch (Exception e) {
                setErrorLiveData("Failed to cancel order: " + e.getMessage());
                Log.e("OrderViewModel", "Cancel order failed: " + e.getMessage(), e);
            }
        }).start();
    }
}
