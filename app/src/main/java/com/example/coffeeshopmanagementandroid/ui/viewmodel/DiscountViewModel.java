package com.example.coffeeshopmanagementandroid.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.discount.request.GetAllDiscountByIdInRequest;
import com.example.coffeeshopmanagementandroid.data.dto.discount.response.DiscountResponse;
import com.example.coffeeshopmanagementandroid.data.mapper.DiscountMapper;
import com.example.coffeeshopmanagementandroid.domain.model.DiscountModel;
import com.example.coffeeshopmanagementandroid.domain.usecase.DiscountUseCase;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.DiscountSortBy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DiscountViewModel extends ViewModel {
    private final DiscountUseCase discountUseCase;

    public DiscountUseCase getDiscountUseCase() {
        return discountUseCase;
    }

    public MutableLiveData<Integer> getDiscountPage() {
        return discountPage;
    }

    public MutableLiveData<Integer> getDiscountLimit() {
        return discountLimit;
    }

    public MutableLiveData<Integer> getDiscountTotal() {
        return discountTotal;
    }

    public MutableLiveData<Boolean> getIsAllDiscountDataLoaded() {
        return isAllDiscountDataLoaded;
    }

    private void setDiscountPage(int page) {
        discountPage.postValue(page);
    }

    private void setIsDiscountLoading(boolean loading) {
        isDiscountLoading.postValue(loading);
    }

    private void setDiscountTotal(Integer total) {
        discountTotal.postValue(total);
    }

    private void setIsAllDiscountDataLoaded(boolean allLoaded) {
        isAllDiscountDataLoaded.postValue(allLoaded);
    }

    private final MutableLiveData<Integer> discountPage = new MutableLiveData<>(1);
    private final MutableLiveData<Integer> discountLimit = new MutableLiveData<>(10);
    private final MutableLiveData<Integer> discountTotal = new MutableLiveData<>(null);
    private final MutableLiveData<Boolean> isDiscountLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isAllDiscountDataLoaded = new MutableLiveData<>(false);

    @Inject
    public DiscountViewModel(DiscountUseCase discountUseCase) {
        this.discountUseCase = discountUseCase;
    }

    private MutableLiveData<List<DiscountModel>> discountsLiveData = new MutableLiveData<>();

    public void FetchDiscountsByIdIn(List<String> discountIds, int page, int limit, SortType sortType, DiscountSortBy sortBy) throws Exception {
        GetAllDiscountByIdInRequest request = new GetAllDiscountByIdInRequest(page, limit, sortType, sortBy, discountIds);
        BasePagingResponse<List<DiscountResponse>> response = discountUseCase.findDiscountsByIdIn(request);
        if (response != null && response.getData() != null) {
            List<DiscountResponse> discountResponses = response.getData();
            List<DiscountModel> discountModels = DiscountMapper.mapToDiscountModels(discountResponses);
            discountsLiveData.postValue(discountModels);
        } else {
            Log.e("DiscountViewModel", "Fetch discounts failed: ");
        }
    }

    public void FetchAllDiscounts(int page, int limit, SortType sortType, DiscountSortBy sortBy) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                BasePagingResponse<List<DiscountResponse>> response = discountUseCase.findAllDiscounts(page, limit, sortType.toString(), sortBy.toString());
                if (response != null && response.getData() != null) {
                    List<DiscountResponse> discountResponses = response.getData();
                    List<DiscountModel> discountModels = DiscountMapper.mapToDiscountModels(discountResponses);
                    discountsLiveData.postValue(discountModels);
                } else {
                    Log.e("DiscountViewModel", "Fetch all discounts failed: ");
                }
            } catch (Exception e) {
                Log.e("DiscountViewModel", "Error fetching discounts", e);
            }
        });
    }

    public void fetchMoreDiscounts(SortType sortType, DiscountSortBy sortBy) {
        if (isAllDiscountDataLoaded.getValue() != null && isAllDiscountDataLoaded.getValue()) {
            Log.d("DiscountViewModel", "All discount data already loaded, skipping fetch");
            return;
        }
        int currentPage = discountPage.getValue() != null ? discountPage.getValue() : 1;
        int nextPage = currentPage + 1;
        setDiscountPage(nextPage);
        fetchAllDiscountsInternal(nextPage, discountLimit.getValue(), sortType, sortBy);
    }

    private void fetchAllDiscountsInternal(int page, Integer limit, SortType sortType, DiscountSortBy sortBy) {
        setIsDiscountLoading(true);
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                BasePagingResponse<List<DiscountResponse>> response = discountUseCase.findAllDiscounts(page, limit, sortType.toString(), sortBy.toString());
                if (response != null && response.getData() != null) {
                    setDiscountTotal(response.getPaging().getTotal());
                    List<DiscountModel> newDiscounts = DiscountMapper.mapToDiscountModels(response.getData());
                    appendDiscounts(newDiscounts);
                }
            } catch (Exception e) {
                Log.e("DiscountViewModel", "Error fetching more discounts", e);
            } finally {
                setIsDiscountLoading(false);
            }
        });
    }

    private void appendDiscounts(List<DiscountModel> newDiscounts) {
        List<DiscountModel> current = discountsLiveData.getValue() != null ? new ArrayList<>(discountsLiveData.getValue()) : new ArrayList<>();
        for (DiscountModel discount : newDiscounts) {
            if (!current.contains(discount)) {
                current.add(discount);
            }
        }
        discountsLiveData.postValue(current);

        // Update isAllDiscountDataLoaded
        if (discountTotal.getValue() != null && current.size() >= discountTotal.getValue()) {
            setIsAllDiscountDataLoaded(true);
        }
    }

    public void fetchDiscountById(String id) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                BaseResponse<DiscountResponse> response = discountUseCase.findDiscountById(id);
                if (response != null && response.getData() != null) {
                    DiscountModel discountModel = DiscountMapper.mapToDiscountModel(response.getData());
                    List<DiscountModel> current = discountsLiveData.getValue() != null ? new ArrayList<>(discountsLiveData.getValue()) : new ArrayList<>();
                    current.add(discountModel);
                    discountsLiveData.postValue(current);
                } else {
                    Log.e("DiscountViewModel", "Fetch discount by ID failed: ");
                }
            } catch (Exception e) {
                Log.e("DiscountViewModel", "Error fetching discount by ID", e);
            }
        });
    }

    public MutableLiveData<List<DiscountModel>> getDiscountsLiveData() {
        return discountsLiveData;
    }

    public MutableLiveData<Boolean> getIsDiscountLoading() {
        return isDiscountLoading;
    }
}
