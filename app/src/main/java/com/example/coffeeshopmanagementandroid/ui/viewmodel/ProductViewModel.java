package com.example.coffeeshopmanagementandroid.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.product.request.GetAllProductsRequest;
import com.example.coffeeshopmanagementandroid.data.dto.product.response.ProductResponse;
import com.example.coffeeshopmanagementandroid.data.mapper.ProductMapper;
import com.example.coffeeshopmanagementandroid.domain.model.product.ProductModel;
import com.example.coffeeshopmanagementandroid.domain.usecase.ProductUseCase;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.ProductSortBy;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProductViewModel extends ViewModel {
    private final ProductUseCase productUseCase;
    private final MutableLiveData<List<ProductModel>> productsLiveData = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Integer> page = new MutableLiveData<>(1);
    private final MutableLiveData<Integer> limit = new MutableLiveData<>(10);
    private final MutableLiveData<Integer> total = new MutableLiveData<>(null);
    private final MutableLiveData<List<ProductModel>> recentlyProductsResultLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isAllDataLoaded = new MutableLiveData<>(false); // Thêm cờ này

    @Inject
    public ProductViewModel(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    public LiveData<List<ProductModel>> getProductsLiveData() {
        return productsLiveData;
    }

    public LiveData<List<ProductModel>> getRecentlyProductsLiveData() {
        return recentlyProductsResultLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getIsAllDataLoaded() { // Thêm getter
        return isAllDataLoaded;
    }

    public void setProductsResult(List<ProductModel> products) {
        productsLiveData.postValue(products);
    }

    public void setRecentlyProductsResult(List<ProductModel> recentlyProducts) {
        recentlyProductsResultLiveData.postValue(recentlyProducts);
    }

    public void setErrorLiveData(String errorMessage) {
        errorLiveData.postValue(errorMessage);
    }

    public void setIsLoading(Boolean loading) {
        isLoading.postValue(loading);
    }

    public void setIsAllDataLoaded(Boolean allDataLoaded){
        isAllDataLoaded.postValue(allDataLoaded);
    }

    // Fetch initial products
    public void fetchAllProducts(int page, int limit, SortType sortType, ProductSortBy sortBy) {
        setIsLoading(true);
        new Thread(() -> {
            try {
                GetAllProductsRequest request = new GetAllProductsRequest(page, limit, sortType, sortBy);
                BasePagingResponse<List<ProductResponse>> result = productUseCase.getAllProducts(request);
                if (result != null && result.getData() != null) {
                    setTotal(result.getPaging().getTotal());
                    List<ProductModel> newProducts = ProductMapper.mapProductResponsesToProductsDomain(result.getData());
                    appendProducts(newProducts);
                }
            } catch (Exception e) {
                setErrorLiveData(e.getMessage());
                Log.e("ProductViewModel", "Fetch all products failed: " + e.getMessage(), e);
            } finally {
                setIsLoading(false);
            }
        }).start();
    }

    public void fetchRecentlyProducts(int page, int limit, SortType sortType, ProductSortBy sortBy) {
        setIsLoading(true);
        new Thread(() -> {
            try {
                GetAllProductsRequest request = new GetAllProductsRequest(page, limit, sortType, sortBy);
                BasePagingResponse<List<ProductResponse>> result = productUseCase.getBestSellingProducts(request);
                if (result != null && result.getData() != null) {
                    List<ProductModel> newProducts = ProductMapper.mapProductResponsesToProductsDomain(result.getData());
                    setRecentlyProductsResult(newProducts);
                }
            } catch (Exception e) {
                setErrorLiveData(e.getMessage());
                Log.e("ProductViewModel", "Fetch recently products failed: " + e.getMessage(), e);
            } finally {
                setIsLoading(false);
            }
        }).start();
    }

    // Fetch more products for infinite scroll
    public void fetchMoreProducts(SortType sortType, ProductSortBy sortBy) {
        if (isAllDataLoaded.getValue() != null && isAllDataLoaded.getValue()) {
            Log.d("ProductViewModel", "All data already loaded, skipping fetch");
            return;
        }
        int currentPage = page.getValue() != null ? page.getValue() : 1;
        int nextPage = currentPage + 1;
        setPage(nextPage);
        fetchAllProducts(nextPage, limit.getValue(), sortType, sortBy);

        // Cập nhật isAllDataLoaded khi đã tải đủ
        if (productsLiveData.getValue() != null && total.getValue() != null && productsLiveData.getValue().size() >= total.getValue()) {
            setIsAllDataLoaded(true);
        }
    }

    public void fetchMoreRecentlyProducts(SortType sortType, ProductSortBy sortBy) {
        // Logic sẽ được thêm sau khi có API riêng
    }

    // Phương thức append dữ liệu mới vào danh sách hiện tại
    private void appendProducts(List<ProductModel> newProducts) {
        List<ProductModel> currentProducts = productsLiveData.getValue() != null ? new ArrayList<>(productsLiveData.getValue()) : new ArrayList<>();
        for (ProductModel newProduct : newProducts) {
            if (!currentProducts.contains(newProduct)) {
                currentProducts.add(newProduct);
            }
        }
        productsLiveData.postValue(currentProducts);
    }

    private void setPage(int page) {
        this.page.postValue(page);
    }

    private void setTotal(int total) {
        this.total.postValue(total);
    }
}