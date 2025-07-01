package com.example.coffeeshopmanagementandroid.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.order.request.GetAllOrdersCustomerRequest;
import com.example.coffeeshopmanagementandroid.data.dto.order.response.OrderResponse;
import com.example.coffeeshopmanagementandroid.data.mapper.OrderMapper;
import com.example.coffeeshopmanagementandroid.domain.model.OrderModel;
import com.example.coffeeshopmanagementandroid.domain.usecase.OrderUseCase;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.OrderSortBy;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class OrderViewModel extends ViewModel {
    private final OrderUseCase orderUseCase;
    private final MutableLiveData<List<OrderModel>> ordersLiveData = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Integer> page = new MutableLiveData<>(1);
    private final MutableLiveData<Integer> limit = new MutableLiveData<>(15);
    private final MutableLiveData<Integer> total = new MutableLiveData<>(null);
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isAllDataLoaded = new MutableLiveData<>(false);

    @Inject
    public OrderViewModel(OrderUseCase orderUseCase) {
        this.orderUseCase = orderUseCase;
    }

    public LiveData<List<OrderModel>> getOrdersLiveData() {
        return ordersLiveData;
    }

    public void setOrdersLiveData(List<OrderModel> orders) {
        ordersLiveData.postValue(orders);
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void setErrorLiveData(String errorMessage) {
        errorLiveData.postValue(errorMessage);
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(Boolean loading) {
        isLoading.postValue(loading);
    }

    public LiveData<Boolean> getIsAllDataLoaded() {
        return isAllDataLoaded;
    }

    public void setIsAllDataLoaded(Boolean allDataLoaded) {
        isAllDataLoaded.postValue(allDataLoaded);
    }

    public LiveData<Integer> getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page.postValue(page);
    }

    public LiveData<Integer> getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit.postValue(limit);
    }

    public LiveData<Integer> getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total.postValue(total);
    }

    public void fetchAllOrdersCustomer(int page, int limit, SortType sortType, OrderSortBy sortBy) {
        setIsLoading(true);
        new Thread(() -> {
            try {
                GetAllOrdersCustomerRequest request = new GetAllOrdersCustomerRequest(page, limit, sortType, sortBy);
                BasePagingResponse<List<OrderResponse>> result = orderUseCase.getAllOrdersCustomer(request);
                if (result != null && result.getData() != null) {
                    setTotal(result.getPaging().getTotal());
                    List<OrderModel> newOrders = OrderMapper.mapOrderResponsesToOrdersDomain(result.getData());
                    appendOrders(newOrders);
                }
            } catch (Exception e) {
                setErrorLiveData(e.getMessage());
                Log.e("OrderViewModel", "Fetch all orders failed: " + e.getMessage(), e);
            } finally {
                setIsLoading(false);
            }
        }).start();
    }
    public void fetchMoreOrdersCustomer(SortType sortType, OrderSortBy sortBy) {
        if (isAllDataLoaded.getValue() != null && isAllDataLoaded.getValue()) {
            Log.d("OrderViewModel", "All data already loaded, skipping fetch");
            return;
        }
        int currentPage = page.getValue() != null ? page.getValue() : 1;
        int nextPage = currentPage + 1;
        setPage(nextPage);
        fetchAllOrdersCustomer(nextPage, limit.getValue(), sortType, sortBy);
        if (ordersLiveData.getValue() != null && total.getValue() != null && ordersLiveData.getValue().size() >= total.getValue()) {
            setIsAllDataLoaded(true);
        }
    }
    // Phương thức append dữ liệu mới vào danh sách hiện tại
    private void appendOrders(List<OrderModel> newOrders) {
        List<OrderModel> currentOrders = ordersLiveData.getValue() != null ? new ArrayList<>(ordersLiveData.getValue()) : new ArrayList<>();
        for (OrderModel newOrder : newOrders) {
            if (!currentOrders.contains(newOrder)) {
                currentOrders.add(newOrder);
            }
        }
        setOrdersLiveData(currentOrders);
    }
}
