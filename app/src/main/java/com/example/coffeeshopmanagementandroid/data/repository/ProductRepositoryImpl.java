package com.example.coffeeshopmanagementandroid.data.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.coffeeshopmanagementandroid.data.api.ProductService;
import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.product.request.GetAllFavoriteProductsUserRequest;
import com.example.coffeeshopmanagementandroid.data.dto.product.request.GetAllProductVariantsRequest;
import com.example.coffeeshopmanagementandroid.data.dto.product.request.GetAllProductsRequest;
import com.example.coffeeshopmanagementandroid.data.dto.product.response.ProductResponse;
import com.example.coffeeshopmanagementandroid.data.dto.product.response.ProductVariantResponse;
import com.example.coffeeshopmanagementandroid.data.mapper.ProductMapper;
import com.example.coffeeshopmanagementandroid.domain.model.product.ProductModel;
import com.example.coffeeshopmanagementandroid.domain.model.product.ProductVariantModel;
import com.example.coffeeshopmanagementandroid.domain.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ProductRepositoryImpl implements ProductRepository {

    private final ProductService productService;

    public ProductRepositoryImpl(ProductService productService) {
        this.productService = productService;
    }

    @SuppressLint("LongLogTag")
    @Override
    public BasePagingResponse<List<ProductResponse>> getAllProducts(GetAllProductsRequest request) throws Exception {
        Log.d("Product RepoIml - getAllProducts", "Called");

        Call<BasePagingResponse<List<ProductResponse>>> call = productService.getAllProducts(request.getPage(), request.getLimit(), request.getSortType(), request.getSortBy());
        Response<BasePagingResponse<List<ProductResponse>>> response = call.execute();

        if (response.isSuccessful() && response.body() != null) {
            Log.d("Response", String.valueOf(response.body().getData()));
            return response.body();
        } else {
            String errorMessage = "Get products failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
            Log.e("GET PRODUCTS", errorMessage);
            throw new Exception(errorMessage);
        }
    }

    @Override
    public List<ProductModel> getAllRecentProducts() {
        return null;
    }
    @SuppressLint("LongLogTag")
    @Override
    public ProductModel getProductById(String id) throws Exception {
        Log.d("Product RepoIml - getDetailProduct", "Called");

        Call<BaseResponse<ProductResponse>> call = productService.getProductById(id);
        Response<BaseResponse<ProductResponse>> response = call.execute();
        if (response.isSuccessful() && response.body() != null) {
            Log.d("Response", String.valueOf(response.body()));
            return ProductMapper.mapProductResponseToProductDomain(response.body().getData());
        } else {
            String errorMessage = "Get detail product failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
            Log.e("GET DETAIL PRODUCT", errorMessage);
            throw new Exception(errorMessage);
        }
    }

    @Override
    public List<ProductVariantModel> getProductVariantsByProductId(GetAllProductVariantsRequest request) throws Exception {
        Call<BasePagingResponse<List<ProductVariantResponse>>> call = productService.getAllProductVariants(request.getProductId(), request.getPage(), request.getLimit(), request.getSortType(), request.getSortBy());
        Response<BasePagingResponse<List<ProductVariantResponse>>> response = call.execute();

        if (response.isSuccessful() && response.body() != null) {
            Log.d("Response", String.valueOf(response.body().getData()));
            List<ProductVariantResponse> listVariants = response.body().getData();
            List<ProductVariantModel> result = new ArrayList<>();
            for (ProductVariantResponse item : listVariants) {
                result.add(ProductMapper.mapProductVariantResponseToProductVariantDomain(item));
            }
            return result;
        } else {
            String errorMessage = "Get products failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
            Log.e("GET PRODUCTS", errorMessage);
            throw new Exception(errorMessage);
        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public BasePagingResponse<List<ProductResponse>> getAllFavoriteProducts(GetAllFavoriteProductsUserRequest request) throws Exception {
        Log.d("Favorite Product RepoIml - getAllFavoriteProductsCustomer", "Called");
        Call<BasePagingResponse<List<ProductResponse>>> call = productService.getAllFavoriteProducts(request.getPage(), request.getLimit(), request.getSortType().toString(), request.getSortBy().toString());
        Response<BasePagingResponse<List<ProductResponse>>> response = call.execute();
        if(response.isSuccessful() && response.body() != null) {
            Log.d("Response", String.valueOf(response.body().getData()));
            return response.body();
        } else {
            String errorMessage = "Get favorite products failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
            Log.e("GET FAVORITE PRODUCTS", errorMessage);
            throw new Exception(errorMessage);
        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public BasePagingResponse<List<ProductResponse>> getBestSellingProducts(GetAllProductsRequest request) throws Exception {
        Log.d("Product RepoIml - getBestSellingProducts", "Called");
        Call<BasePagingResponse<List<ProductResponse>>> call = productService.getBestSellingProducts(request.getPage(), request.getLimit(), request.getSortType(), request.getSortBy());
        Response<BasePagingResponse<List<ProductResponse>>> response = call.execute();
        if (response.isSuccessful() && response.body() != null) {
            Log.d("Response", String.valueOf(response.body().getData()));
            return response.body();
        } else {
            String errorMessage = "Get best selling products failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
            Log.e("GET BEST SELLING PRODUCTS", errorMessage);
            throw new Exception(errorMessage);
        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public void addProductToFavorite(String drinkId) throws Exception {
        Log.d("Product RepoIml - addProductToFavorite", "Called");
        Call<BaseResponse<Void>> call = productService.addProductToFavorite(drinkId);
        Response<BaseResponse<Void>> response = call.execute();
        if (response.isSuccessful() && response.body() != null) {
            Log.d("Response", "Product added to favorite successfully");
        } else {
            String errorMessage = "Add product to favorite failed: " + (response.errorBody() != null ? response.errorBody().string() : "Unknown error");
            Log.e("ADD PRODUCT TO FAVORITE", errorMessage);
            throw new Exception(errorMessage);
        }
    }
}