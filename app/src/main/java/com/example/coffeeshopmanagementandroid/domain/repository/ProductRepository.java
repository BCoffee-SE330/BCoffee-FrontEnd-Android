package com.example.coffeeshopmanagementandroid.domain.repository;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.product.request.GetAllFavoriteProductsUserRequest;
import com.example.coffeeshopmanagementandroid.data.dto.product.request.GetAllProductVariantsRequest;
import com.example.coffeeshopmanagementandroid.data.dto.product.request.GetAllProductsRequest;
import com.example.coffeeshopmanagementandroid.data.dto.product.response.ProductResponse;
import com.example.coffeeshopmanagementandroid.domain.model.product.ProductModel;
import com.example.coffeeshopmanagementandroid.domain.model.product.ProductVariantModel;

import java.util.List;

public interface ProductRepository {
    BasePagingResponse<List<ProductResponse>> getAllProducts(GetAllProductsRequest request) throws Exception;

    List<ProductModel> getAllRecentProducts() throws Exception;
    ProductModel getProductById(String id) throws Exception;

    List<ProductVariantModel> getProductVariantsByProductId(GetAllProductVariantsRequest request) throws Exception;

    BasePagingResponse<List<ProductResponse>> getAllFavoriteProducts(GetAllFavoriteProductsUserRequest request) throws Exception;

    BasePagingResponse<List<ProductResponse>> getBestSellingProducts(GetAllProductsRequest request) throws Exception;

    void addProductToFavorite(String drinkId) throws Exception;

}