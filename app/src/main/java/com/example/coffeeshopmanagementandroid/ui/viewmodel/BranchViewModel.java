package com.example.coffeeshopmanagementandroid.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffeeshopmanagementandroid.data.dto.branch.request.GetAllBranchRequest;
import com.example.coffeeshopmanagementandroid.data.mapper.BranchMapper;
import com.example.coffeeshopmanagementandroid.domain.model.branch.BranchModel;
import com.example.coffeeshopmanagementandroid.domain.usecase.BranchUseCase;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.OrderSortBy;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BranchViewModel extends ViewModel {
    private final BranchUseCase branchUseCase;

    private MutableLiveData<List<BranchModel>> branchModelsLiveData = new MutableLiveData<>();

    private MutableLiveData<BranchModel> branchModelLiveData = new MutableLiveData<>();

    @Inject
    public BranchViewModel(BranchUseCase branchUseCase) {
        this.branchUseCase = branchUseCase;
    }

    public MutableLiveData<List<BranchModel>> getBranchModelsLiveData() {
        return branchModelsLiveData;
    }

    public MutableLiveData<BranchModel> getBranchModelLiveData() {
        return branchModelLiveData;
    }

    public void setBranchModelsLiveData(MutableLiveData<List<BranchModel>> branchModelsLiveData, MutableLiveData<BranchModel> branchModelLiveData) {
        this.branchModelsLiveData = branchModelsLiveData;
        this.branchModelLiveData = branchModelLiveData;
    }

    public void fetchBranches(int page, int limit, SortType sortType, OrderSortBy sortBy) {
        new Thread(() -> {
            try {
                GetAllBranchRequest request = new GetAllBranchRequest(page, limit, sortType, sortBy);
                var response = branchUseCase.getAllBranches(request);
                var branchModels = BranchMapper.mapToBranchModels(response.getData());
                branchModelsLiveData.postValue(branchModels);
            } catch (Exception e) {
                e.printStackTrace();
                branchModelsLiveData.postValue(null);
            }
        }).start();
    }

    public void fetchBranchById(String id) {
        new Thread(() -> {
            try {
                var branchResponse = branchUseCase.getBranchById(id);
                var branchModel = BranchMapper.mapToBranchModel(branchResponse);
                branchModelLiveData.postValue(branchModel);
            } catch (Exception e) {
                e.printStackTrace();
                branchModelsLiveData.postValue(null);
            }
        }).start();
    }
}
