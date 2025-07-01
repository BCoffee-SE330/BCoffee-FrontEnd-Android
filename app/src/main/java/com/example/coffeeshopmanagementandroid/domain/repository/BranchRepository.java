package com.example.coffeeshopmanagementandroid.domain.repository;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.branch.request.GetAllBranchRequest;
import com.example.coffeeshopmanagementandroid.data.dto.branch.response.BranchResponse;

import java.util.List;

public interface BranchRepository {
    BasePagingResponse<List<BranchResponse>> getAllBranches(GetAllBranchRequest request) throws Exception;
}
