package com.example.coffeeshopmanagementandroid.data.mapper;

import com.example.coffeeshopmanagementandroid.data.dto.order.response.OrderDetailResponse;
import com.example.coffeeshopmanagementandroid.data.dto.order.response.OrderResponse;
import com.example.coffeeshopmanagementandroid.domain.model.OrderModel;
import com.example.coffeeshopmanagementandroid.domain.model.cart.CartItemModel;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {
    public static List<OrderModel> mapOrderResponsesToOrdersDomain(List<OrderResponse> orderResponses) {
        List<OrderModel> orderModels = new ArrayList<>();
        for (OrderResponse response : orderResponses) {
            OrderModel model = new OrderModel();
            // Chuyển các trường từ OrderResponse sang OrderModel
            model.setOrderId(response.getOrderId());
            model.setUserId(response.getUserId());
            model.setEmployeeId(response.getEmployeeId());
            model.setShippingAddressId(response.getShippingAddressId());
            model.setPaymentMethodId(response.getPaymentMethodId());
            model.setOrderTotalCost(response.getOrderTotalCost());
            model.setOrderDiscountCost(response.getOrderDiscountCost());
            model.setOrderTotalCostAfterDiscount(response.getOrderTotalCostAfterDiscount());
            model.setOrderStatus(response.getOrderStatus());
            model.setOrderTrackingNumber(response.getOrderTrackingNumber());
            if (response.getApprovalLink() != null)
                model.setApprovalLink(response.getApprovalLink());
            else
                model.setApprovalLink(null);
            orderModels.add(model);
        }
        return orderModels;
    }

    public static CartItemModel mapOrderDetailResponseToCartItemModel(OrderDetailResponse orderDetailResponse) {
        CartItemModel cartItemModel = new CartItemModel();
        cartItemModel.setCartDetailQuantity(orderDetailResponse.getOrderDetailQuantity());
        cartItemModel.setCartDetailUnitPrice(orderDetailResponse.getOrderDetailUnitPrice().doubleValue());
        cartItemModel.setCartDetailTotalPrice(orderDetailResponse.getOrderDetailUnitPrice().doubleValue() * orderDetailResponse.getOrderDetailQuantity());
        cartItemModel.setVariantId(orderDetailResponse.getProductVariantId());
        cartItemModel.setProductName(orderDetailResponse.getProductName());
        cartItemModel.setProductThumb(orderDetailResponse.getProductThumb());
        cartItemModel.setVariantTierIdx(orderDetailResponse.getVariantTierId());
        return cartItemModel;
    }
    public static List<CartItemModel> mapOrderDetailResponsesToCartItems(List<OrderDetailResponse> orderDetailResponses) {
        List<CartItemModel> cartItems = new ArrayList<>();
        for (OrderDetailResponse response : orderDetailResponses) {
            CartItemModel item = mapOrderDetailResponseToCartItemModel(response);
            cartItems.add(item);
        }
        return cartItems;
    }
}
