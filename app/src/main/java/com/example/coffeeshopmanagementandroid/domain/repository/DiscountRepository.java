package com.example.coffeeshopmanagementandroid.domain.repository;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.discount.request.GetAllDiscountByIdInRequest;
import com.example.coffeeshopmanagementandroid.data.dto.discount.response.DiscountResponse;

import java.util.List;

public interface DiscountRepository {
    BasePagingResponse<List<DiscountResponse>> findDiscountsByIdIn(GetAllDiscountByIdInRequest request) throws Exception;
    BasePagingResponse<List<DiscountResponse>> findAllDiscounts(int page, int limit, String sortType, String sortBy) throws Exception;
    BaseResponse<DiscountResponse> findDiscountById(String id) throws Exception;
}
