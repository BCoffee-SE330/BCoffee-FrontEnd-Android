package com.example.coffeeshopmanagementandroid.data.mapper;

import com.example.coffeeshopmanagementandroid.data.dto.branch.response.BranchResponse;
import com.example.coffeeshopmanagementandroid.domain.model.branch.BranchModel;

import java.util.ArrayList;
import java.util.List;

public class BranchMapper {
    public static BranchModel mapToBranchModel(BranchResponse response) {
        if (response == null) {
            return null;
        }
        return new BranchModel(
                response.getId(),
                response.getCreatedAt(),
                response.getUpdatedAt(),
                response.getBranchName(),
                response.getBranchAddress(),
                response.getBranchPhone(),
                response.getBranchEmail(),
                response.getManagerId(),
                response.getManagerName()
        );
    }

    public static List<BranchModel> mapToBranchModels(List<BranchResponse> responses) {
        List<BranchModel> branchModels = new ArrayList<>();
        if (responses != null) {
            for (BranchResponse response : responses) {
                BranchModel model = mapToBranchModel(response);
                if (model != null) {
                    branchModels.add(model);
                }
            }
        }
        return branchModels;
    }
}