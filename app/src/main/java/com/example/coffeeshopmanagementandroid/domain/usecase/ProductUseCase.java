package com.example.coffeeshopmanagementandroid.domain.usecase;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.product.request.GetAllFavoriteProductsUserRequest;
import com.example.coffeeshopmanagementandroid.data.dto.product.request.GetAllProductVariantsRequest;
import com.example.coffeeshopmanagementandroid.data.dto.product.request.GetAllProductsRequest;
import com.example.coffeeshopmanagementandroid.data.dto.product.response.ProductResponse;
import com.example.coffeeshopmanagementandroid.domain.model.product.ProductModel;
import com.example.coffeeshopmanagementandroid.domain.model.product.ProductVariantModel;
import com.example.coffeeshopmanagementandroid.domain.repository.ProductRepository;

import java.util.List;

public class ProductUseCase {
    private final ProductRepository productRepository;

    public ProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public BasePagingResponse<List<ProductResponse>> getAllProducts(GetAllProductsRequest request) throws Exception {
        Log.d("Product Use Case", "Called");
        return productRepository.getAllProducts(request);
    }

    public ProductModel getProductById(String id) throws Exception {
        return productRepository.getProductById(id);
    }

    public List<ProductModel> getRecentProducts() throws Exception {
        return productRepository.getAllRecentProducts();
    }

    public List<ProductVariantModel> getAllProductVariants(GetAllProductVariantsRequest request) throws Exception {
        Log.d("Product Use Case", "Called");
        return productRepository.getProductVariantsByProductId(request);
    }

    @SuppressLint("LongLogTag")
    public BasePagingResponse<List<ProductResponse>> getAllFavoriteProducts(GetAllFavoriteProductsUserRequest request) throws Exception {
        Log.d("Favorite Product Use Case", "Called");
        return productRepository.getAllFavoriteProducts(request);
    }

    @SuppressLint("LongLogTag")
    public BasePagingResponse<List<ProductResponse>> getBestSellingProducts(GetAllProductsRequest request) throws Exception {
        Log.d("Best Selling Product Use Case", "Called");
        return productRepository.getBestSellingProducts(request);
    }

    public void addProductToFavorite(String drinkId) throws Exception {
        productRepository.addProductToFavorite(drinkId);
    }
}
