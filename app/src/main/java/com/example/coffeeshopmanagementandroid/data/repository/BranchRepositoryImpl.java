package com.example.coffeeshopmanagementandroid.data.repository;

import com.example.coffeeshopmanagementandroid.data.api.BranchService;
import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.branch.request.GetAllBranchRequest;
import com.example.coffeeshopmanagementandroid.data.dto.branch.response.BranchResponse;
import com.example.coffeeshopmanagementandroid.domain.repository.BranchRepository;

import java.util.List;

public class BranchRepositoryImpl implements BranchRepository {

    private final BranchService branchService;

    public BranchRepositoryImpl(BranchService branchService) {
        this.branchService = branchService;
    }

    @Override
    public BasePagingResponse<List<BranchResponse>> getAllBranches(GetAllBranchRequest request) throws Exception {
        try {
            return branchService.getAllBranches(request.getPage(), request.getLimit(), request.getSortType().toString(), request.getSortBy().toString()).execute().body();
        } catch (Exception e) {
            throw new Exception("Failed to fetch branches", e);
        }
    }
}
