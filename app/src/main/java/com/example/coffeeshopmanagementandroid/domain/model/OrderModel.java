package com.example.coffeeshopmanagementandroid.domain.model;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.coffeeshopmanagementandroid.utils.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public class OrderModel {
    private String orderId;
    private String userId;
    private String employeeId;
    private String shippingAddressId;
    private String paymentMethodId;
    private BigDecimal orderTotalCost;
    private BigDecimal orderDiscountCost;
    private BigDecimal orderTotalCostAfterDiscount;
    private String orderStatus;
    private String orderTrackingNumber;
    private String approvalLink;

    public OrderModel() {
        // Constructor mặc định không cần thực hiện gì
    }

    public OrderModel(String orderId, String userId, String employeeId, String shippingAddressId, String paymentMethodId, BigDecimal orderTotalCost, BigDecimal orderDiscountCost, BigDecimal orderTotalCostAfterDiscount, String orderStatus, String orderTrackingNumber) {
        this.orderId = orderId;
        this.userId = userId;
        this.employeeId = employeeId;
        this.shippingAddressId = shippingAddressId;
        this.paymentMethodId = paymentMethodId;
        this.orderTotalCost = orderTotalCost;
        this.orderDiscountCost = orderDiscountCost;
        this.orderTotalCostAfterDiscount = orderTotalCostAfterDiscount;
        this.orderStatus = orderStatus;
        this.orderTrackingNumber = orderTrackingNumber;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(String shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public BigDecimal getOrderTotalCost() {
        return orderTotalCost;
    }

    public void setOrderTotalCost(BigDecimal orderTotalCost) {
        this.orderTotalCost = orderTotalCost;
    }

    public BigDecimal getOrderDiscountCost() {
        return orderDiscountCost;
    }

    public void setOrderDiscountCost(BigDecimal orderDiscountCost) {
        this.orderDiscountCost = orderDiscountCost;
    }

    public BigDecimal getOrderTotalCostAfterDiscount() {
        return orderTotalCostAfterDiscount;
    }

    public void setOrderTotalCostAfterDiscount(BigDecimal orderTotalCostAfterDiscount) {
        this.orderTotalCostAfterDiscount = orderTotalCostAfterDiscount;
    }

    public String getOrderStatus() {
        switch (this.orderStatus) {
            case "ĐANG CHỜ":
                return OrderStatus.PENDING.getStatus();
            case "ĐANG XỬ LÝ":
                return OrderStatus.PROCESSING.getStatus();
            case "ĐANG GIAO HÀNG":
                return OrderStatus.DELIVERING.getStatus();
            case "ĐÃ GIAO HÀNG":
                return OrderStatus.DELIVERED.getStatus();
            case "HOÀN TẤT":
                return OrderStatus.COMPLETED.getStatus();
            default:
                return OrderStatus.CANCELLED.getStatus();
        }
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderTrackingNumber() {
        return orderTrackingNumber;
    }

    public void setOrderTrackingNumber(String orderTrackingNumber) {
        this.orderTrackingNumber = orderTrackingNumber;
    }

    public String getApprovalLink() {
        return approvalLink;
    }

    public void setApprovalLink(String approvalLink) {
        this.approvalLink = approvalLink;
    }
}
