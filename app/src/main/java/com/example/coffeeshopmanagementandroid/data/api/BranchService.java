package com.example.coffeeshopmanagementandroid.data.api;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.branch.response.BranchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BranchService {
    @GET("branch/all")
    Call<BasePagingResponse<List<BranchResponse>>> getAllBranches(@Query("page") int page,
                                                                  @Query("limit") int limit,
                                                                  @Query("sortType") String sortType,
                                                                  @Query("sortBy") String sortBy);
}
