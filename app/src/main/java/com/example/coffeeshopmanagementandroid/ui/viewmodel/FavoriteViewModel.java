package com.example.coffeeshopmanagementandroid.ui.viewmodel;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.category.request.GetAllCategoriesRequest;
import com.example.coffeeshopmanagementandroid.data.dto.category.response.CategoryResponse;
import com.example.coffeeshopmanagementandroid.data.dto.product.request.GetAllFavoriteProductsUserRequest;
import com.example.coffeeshopmanagementandroid.data.dto.product.response.ProductResponse;
import com.example.coffeeshopmanagementandroid.data.mapper.CategoryMapper;
import com.example.coffeeshopmanagementandroid.data.mapper.ProductMapper;
import com.example.coffeeshopmanagementandroid.domain.model.CategoryModel;
import com.example.coffeeshopmanagementandroid.domain.model.product.ProductModel;
import com.example.coffeeshopmanagementandroid.domain.usecase.CategoryUseCase;
import com.example.coffeeshopmanagementandroid.domain.usecase.ProductUseCase;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.CategorySortBy;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.FavoriteProductSortBy;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class FavoriteViewModel extends ViewModel {
    ProductUseCase productUseCase;
    CategoryUseCase categoryUseCase;
    private final MutableLiveData<List<ProductModel>> favoriteProductsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<ProductModel>> detailFavoriteProductsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<CategoryModel>> categoriesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> page = new MutableLiveData<>(1);
    private final MutableLiveData<Integer> limit = new MutableLiveData<>(10);
    private final MutableLiveData<Integer> total = new MutableLiveData<>(null);
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isAllDataLoaded = new MutableLiveData<>(false);

    @Inject
    public FavoriteViewModel(ProductUseCase productUseCase, CategoryUseCase categoryUseCase) {
        this.productUseCase = productUseCase;
        this.categoryUseCase = categoryUseCase;
    }

    public MutableLiveData<List<ProductModel>> getFavoriteProductsLiveData() {
        return favoriteProductsLiveData;
    }

    public void setFavoriteProductsLiveData(List<ProductModel> favoriteProducts) {
        favoriteProductsLiveData.postValue(favoriteProducts);
    }

    public MutableLiveData<List<ProductModel>> getDetailFavoriteProductsLiveData() {
        return detailFavoriteProductsLiveData;
    }

    public void setDetailFavoriteProducts(List<ProductModel> favoriteProducts) {
        detailFavoriteProductsLiveData.postValue(favoriteProducts);
    }

    public MutableLiveData<List<CategoryModel>> getCategoriesLiveData() {
        return categoriesLiveData;
    }

    public void setCategoriesLiveData(List<CategoryModel> categories) {
        categoriesLiveData.postValue(categories);
    }

    public MutableLiveData<Integer> getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page.postValue(page);
    }

    public MutableLiveData<Integer> getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit.postValue(limit);
    }

    public MutableLiveData<Integer> getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total.postValue(total);
    }

    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void setErrorLiveData(String errorMessage) {
        errorLiveData.postValue(errorMessage);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(Boolean loading) {
        isLoading.postValue(loading);
    }

    public MutableLiveData<Boolean> getIsAllDataLoaded() {
        return isAllDataLoaded;
    }

    public void setIsAllDataLoaded(Boolean allDataLoaded) {
        isAllDataLoaded.postValue(allDataLoaded);
    }

    @SuppressLint("LongLogTag")
    public void fetchAllFavoriteProducts(String userId, int page, int limit, SortType sortType, FavoriteProductSortBy sortBy) {
        setIsLoading(true);
        new Thread(() -> {
            try {
                GetAllFavoriteProductsUserRequest request = new GetAllFavoriteProductsUserRequest(page, limit, userId, sortType, sortBy);
                BasePagingResponse<List<ProductResponse>> result = productUseCase.getAllFavoriteProducts(request);
                if (result != null && result.getData() != null) {
                    setTotal(result.getPaging().getTotal());
                    // Set FavoriteProductId
                    List<ProductModel> newFavoriteProducts = ProductMapper.mapProductResponsesToProductsDomain(result.getData());
                    appendFavoriteProducts(newFavoriteProducts);
                    // Set DetailFavoriteProduct
                    List<ProductModel> newDetailFavoriteProducts = new ArrayList<>();
                    for (ProductModel favoriteProductId : newFavoriteProducts) {
                        ProductModel detailFavoriteProduct = fetchDetailFavoriteProduct(favoriteProductId.getProductId());
                        newDetailFavoriteProducts.add(detailFavoriteProduct);
                    }
                    appendDetailFavoriteProducts(newDetailFavoriteProducts);

                }
            } catch (Exception e) {
                setErrorLiveData(e.getMessage());
                Log.e("FavoriteProductViewModel", "Fetch all orders failed: " + e.getMessage(), e);
            } finally {
                setIsLoading(false);
            }
        }).start();
    }

    private void appendFavoriteProducts(List<ProductModel> newProducts) {
        List<ProductModel> currentProducts = favoriteProductsLiveData.getValue() != null ? new ArrayList<>(favoriteProductsLiveData.getValue()) : new ArrayList<>();
        for (ProductModel newProduct : newProducts) {
            if (!currentProducts.contains(newProduct)) {
                currentProducts.add(newProduct);
            }
        }
        favoriteProductsLiveData.postValue(currentProducts);
    }

    private ProductModel fetchDetailFavoriteProduct(String productId) throws Exception {
        return productUseCase.getProductById(productId);
    }

    private void appendDetailFavoriteProducts(List<ProductModel> newProducts) {
        List<ProductModel> currentDetailProducts = detailFavoriteProductsLiveData.getValue() != null ? new ArrayList<>(detailFavoriteProductsLiveData.getValue()) : new ArrayList<>();
        for (ProductModel newProduct : newProducts) {
            if (!currentDetailProducts.contains(newProduct)) {
                currentDetailProducts.add(newProduct);
            }
        }
        detailFavoriteProductsLiveData.postValue(currentDetailProducts);
    }

    public void fetchAllCategories(int page, int limit, SortType sortType, CategorySortBy sortBy) {
        setIsLoading(true);
        new Thread(() -> {
            try {
                GetAllCategoriesRequest request = new GetAllCategoriesRequest(page, limit, sortType, sortBy);
                BasePagingResponse<List<CategoryResponse>> result = categoryUseCase.getAllCategories(request);
                if (result != null && result.getData() != null) {
                    setTotal(result.getPaging().getTotal());
                    List<CategoryModel> newCategory = CategoryMapper.mapCategoryResponsesToCategoriesDomain(result.getData());
                    appendCategories(newCategory);
                }
            } catch (Exception e) {
                setErrorLiveData(e.getMessage());
                Log.e("CategoryViewModel", "Fetch all categories failed: " + e.getMessage(), e);
            } finally {
                setIsLoading(false);
            }
        }).start();
    }

    // Fetch more products for infinite scroll
    public void fetchMoreCategories(SortType sortType, CategorySortBy sortBy) {
        if (isAllDataLoaded.getValue() != null && isAllDataLoaded.getValue()) {
            Log.d("CategoryViewModel", "All data already loaded, skipping fetch");
            return;
        }
        int currentPage = page.getValue() != null ? page.getValue() : 1;
        int nextPage = currentPage + 1;
        int currentLimit = (limit.getValue() != null) ? limit.getValue() : 10;
        setPage(nextPage);
        fetchAllCategories(nextPage, currentLimit, sortType, sortBy);

        // Cập nhật isAllDataLoaded khi đã tải đủ
        if (categoriesLiveData.getValue() != null && total.getValue() != null && categoriesLiveData.getValue().size() >= total.getValue()) {
            setIsAllDataLoaded(true);
        }
    }

    // Phương thức append dữ liệu mới vào danh sách hiện tại
    private void appendCategories(List<CategoryModel> newCategories) {
        List<CategoryModel> currentCategories = categoriesLiveData.getValue() != null ? new ArrayList<>(categoriesLiveData.getValue()) : new ArrayList<>();
        for (CategoryModel newCategory : newCategories) {
            if (!currentCategories.contains(newCategory)) {
                currentCategories.add(newCategory);
            }
        }
        // Thêm tất cả
        CategoryModel allCategory = new CategoryModel();
        allCategory.setCategoryId("all");
        allCategory.setCategoryName("Tất cả");
        allCategory.setCategoryDescription("Tất cả các loại sản phẩm");

        if (!currentCategories.contains(allCategory)) {
            currentCategories.add(0, allCategory); // Thêm vào đầu danh sách
        }

        categoriesLiveData.postValue(currentCategories);
    }

}
