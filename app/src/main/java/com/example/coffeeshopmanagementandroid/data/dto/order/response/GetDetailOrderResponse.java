package com.example.coffeeshopmanagementandroid.data.dto.order.response;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.List;

public class GetDetailOrderResponse {
    private String id;

    private String createdAt;

    private String updatedAt;

    private BigDecimal orderTotalCost;
    private BigDecimal orderDiscountCost;
    private BigDecimal orderTotalCostAfterDiscount;
    private String orderStatus;
    private String orderTrackingNumber;
    private String userName;
    private String userPhoneNumber;
    private String shippingAddressName;
    private String approvalLink;

    @SerializedName("orderDetails")
    private List<OrderDetailResponse> orderDetails;

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
        return orderStatus;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getShippingAddressName() {
        return shippingAddressName;
    }

    public void setShippingAddressName(String shippingAddressName) {
        this.shippingAddressName = shippingAddressName;
    }

    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetails;
    }

    public String getApprovalLink() {
        return approvalLink;
    }

    public void setApprovalLink(String approvalLink) {
        this.approvalLink = approvalLink;
    }
}
