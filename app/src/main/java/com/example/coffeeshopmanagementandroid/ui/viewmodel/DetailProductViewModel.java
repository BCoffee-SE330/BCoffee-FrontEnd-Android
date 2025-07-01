package com.example.coffeeshopmanagementandroid.ui.viewmodel;

import android.os.Build;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffeeshopmanagementandroid.data.dto.cart.request.AddToCartRequest;
import com.example.coffeeshopmanagementandroid.data.dto.product.request.GetAllProductVariantsRequest;
import com.example.coffeeshopmanagementandroid.domain.model.product.ProductModel;
import com.example.coffeeshopmanagementandroid.domain.model.product.ProductVariantModel;
import com.example.coffeeshopmanagementandroid.domain.usecase.CartUseCase;
import com.example.coffeeshopmanagementandroid.domain.usecase.ProductUseCase;
import com.example.coffeeshopmanagementandroid.ui.fragment.product.DetailProductFragment;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.ProductVariantSortBy;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DetailProductViewModel extends ViewModel {

    private final ProductUseCase productUseCase;
    private final CartUseCase cartUseCase;
    private final MutableLiveData<ProductModel> productLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<ProductVariantModel>> variantListLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    @Inject
    public DetailProductViewModel(ProductUseCase productUseCase, CartUseCase cartUseCase) {
        this.productUseCase = productUseCase;
        this.cartUseCase = cartUseCase;
    }

    public LiveData<ProductModel> getProductLiveData() {
        return productLiveData;
    }

    public LiveData<List<ProductVariantModel>> getVariantListLiveData() {
        return variantListLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(Boolean loading) {
        isLoading.postValue(loading);
    }

    public void setErrorLiveData(String errorMessage) {
        errorLiveData.postValue(errorMessage);
    }

    public void fetchProductDetailAndVariants(String productId) {
        setIsLoading(true);
        new Thread(() -> {
            try {
                // Fetch product detail
                ProductModel product = productUseCase.getProductById(productId);
                productLiveData.postValue(product);

                // Fetch variant list by productId
                if (product == null) {
                    setErrorLiveData("Product not found");
                    return;
                }
                GetAllProductVariantsRequest request = new GetAllProductVariantsRequest(productId, 1, 100, SortType.DESC, ProductVariantSortBy.CREATED_AT);
                List<ProductVariantModel> variants = productUseCase.getAllProductVariants(request);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                    variantListLiveData.postValue(variants.reversed());
                }
                else {
                    variantListLiveData.postValue(variants);
                }

            } catch (Exception e) {
                setErrorLiveData(e.getMessage());
                Log.e("DetailProductViewModel", "Failed to fetch product detail or variants: " + e.getMessage(), e);
            } finally {
                setIsLoading(false);
            }
        }).start();
    }

    public void addToCart(String variantId, int quantity, DetailProductFragment.AddToCartCallback callback) {
        new Thread(() -> {
            try {
                String result = cartUseCase.addToCart(new AddToCartRequest(variantId, quantity));
                callback.onSuccess(result);
            } catch (Exception e) {
                callback.onError(e.getMessage());
            }
        }).start();
    }

    public void addProductToFavorite(String drinkId) {
        new Thread(() -> {
            try {
                productUseCase.addProductToFavorite(drinkId);
            } catch (Exception e) {
                Log.e("DetailProductViewModel", "Failed to add product to favorite: " + e.getMessage(), e);
            }
        }).start();
    }
}
