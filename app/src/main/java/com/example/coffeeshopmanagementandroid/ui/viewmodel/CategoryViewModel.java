package com.example.coffeeshopmanagementandroid.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.category.request.GetAllCategoriesRequest;
import com.example.coffeeshopmanagementandroid.data.dto.category.response.CategoryResponse;
import com.example.coffeeshopmanagementandroid.data.mapper.CategoryMapper;
import com.example.coffeeshopmanagementandroid.domain.model.CategoryModel;
import com.example.coffeeshopmanagementandroid.domain.usecase.CategoryUseCase;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.CategorySortBy;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CategoryViewModel extends ViewModel {
    private final CategoryUseCase categoryUseCase;
    private final MutableLiveData<List<CategoryModel>> categoriesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> page = new MutableLiveData<>(1);
    private final MutableLiveData<Integer> limit = new MutableLiveData<>(10);
    private final MutableLiveData<Integer> total = new MutableLiveData<>(null);
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isAllDataLoaded = new MutableLiveData<>(false); // Thêm cờ này

    @Inject
    public CategoryViewModel(CategoryUseCase categoryUseCase) {
        this.categoryUseCase = categoryUseCase;
    }

    public LiveData<List<CategoryModel>> getCategoriesLiveData() {
        return categoriesLiveData;
    }

    public CategoryUseCase getCategoryUseCase() {
        return categoryUseCase;
    }

    public MutableLiveData<Integer> getPage() {
        return page;
    }

    public MutableLiveData<Integer> getLimit() {
        return limit;
    }

    public MutableLiveData<Integer> getTotal() {
        return total;
    }

    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getIsAllDataLoaded() {
        return isAllDataLoaded;
    }

    public void setCategoriesLiveData(List<CategoryModel> categories) {
        categoriesLiveData.postValue(categories);
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

    public void setPage(int page) {
        this.page.postValue(page);
    }

    public void setTotal(int total) {
        this.total.postValue(total);
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
