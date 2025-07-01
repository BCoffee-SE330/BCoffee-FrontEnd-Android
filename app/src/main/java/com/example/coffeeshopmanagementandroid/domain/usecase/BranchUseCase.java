package com.example.coffeeshopmanagementandroid.domain.usecase;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.branch.request.GetAllBranchRequest;
import com.example.coffeeshopmanagementandroid.data.dto.branch.response.BranchResponse;
import com.example.coffeeshopmanagementandroid.domain.repository.BranchRepository;

import java.util.List;

public class BranchUseCase {
    private final BranchRepository branchRepository;
    public BranchUseCase(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public BasePagingResponse<List<BranchResponse>> getAllBranches(GetAllBranchRequest request) throws Exception {
        return branchRepository.getAllBranches(request);
    }
}
