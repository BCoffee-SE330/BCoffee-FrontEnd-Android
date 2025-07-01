package com.example.coffeeshopmanagementandroid.data.mapper;

import com.example.coffeeshopmanagementandroid.data.dto.product.response.ProductResponse;
import com.example.coffeeshopmanagementandroid.data.dto.product.response.ProductVariantResponse;
import com.example.coffeeshopmanagementandroid.domain.model.product.ProductModel;
import com.example.coffeeshopmanagementandroid.domain.model.product.ProductVariantModel;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {
    public static List<ProductModel> mapProductResponsesToProductsDomain(List<ProductResponse> productResponses) {
        List<ProductModel> productModels = new ArrayList<>();
        for (ProductResponse response : productResponses) {
            ProductModel model = new ProductModel();
            // Chuyển các trường từ ProductResponse sang ProductModel
            model.setProductId(response.getProductId());
            model.setProductName(response.getProductName());
            model.setProductDescription(response.getProductDescription());
            model.setProductPrice(response.getProductPrice());
            model.setProductThumb(response.getProductThumb());
            model.setProductCategoryId(response.getProductCategoryId());
            model.setProductSlug(response.getProductSlug());
            model.setProductIsPublished(response.getProductIsPublished());
            model.setProductIsDeleted(response.getProductIsDeleted());
            model.setProductRatingsAverage(response.getProductRatingsAverage());
            productModels.add(model);
        }
        return productModels;
    }
    public static ProductModel mapProductResponseToProductDomain(ProductResponse productResponse) {
        ProductModel model = new ProductModel();
        // Chuyển các trường từ ProductResponse sang ProductModel
        model.setProductId(productResponse.getProductId());
        model.setProductName(productResponse.getProductName());
        model.setProductDescription(productResponse.getProductDescription());
        model.setProductPrice(productResponse.getProductPrice());
        model.setProductThumb(productResponse.getProductThumb());
        model.setProductCategoryId(productResponse.getProductCategoryId());
        model.setProductSlug(productResponse.getProductSlug());
        model.setProductIsPublished(productResponse.getProductIsPublished());
        model.setProductIsDeleted(productResponse.getProductIsDeleted());
        model.setProductRatingsAverage(productResponse.getProductRatingsAverage());
        if (productResponse.getIsFavorite()) {
            model.setFavorite(true);
        } else {
            model.setFavorite(false);
        }
        return model;
    }

    public static ProductVariantModel mapProductVariantResponseToProductVariantDomain(ProductVariantResponse response) {
        ProductVariantModel model = new ProductVariantModel();

        model.setId(response.getId());
        model.setProductId(response.getProductId());
        model.setVariantDefault(response.getVariantDefault());
        model.setVariantPrice(response.getVariantPrice());
        model.setVariantSlug(response.getVariantSlug());
        model.setVariantSort(response.getVariantSort());
        model.setVariantTierIdx(response.getVariantTierIdx());

        return model;
    }
}
