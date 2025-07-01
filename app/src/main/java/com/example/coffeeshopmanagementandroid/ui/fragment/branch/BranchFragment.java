package com.example.coffeeshopmanagementandroid.ui.fragment.branch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.branch.BranchModel;
import com.example.coffeeshopmanagementandroid.ui.MainActivity;
import com.example.coffeeshopmanagementandroid.ui.adapter.ChooseBranchAdapter;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.BranchViewModel;
import com.example.coffeeshopmanagementandroid.utils.SpaceItemDecoration;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.OrderSortBy;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BranchFragment  extends Fragment {

    private RecyclerView branchRecyclerView;
    private ChooseBranchAdapter branchAdapter;
    private NavController navController;
    private BranchViewModel branchViewModel;
    private ImageButton backButton;
    private BranchModel selectedBranch;
    private Button applyButton;

    protected int getLayoutResId() {
        return R.layout.fragment_choose_branch;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    protected void initViews(View view) {
        branchViewModel = new ViewModelProvider(this).get(BranchViewModel.class);
        setUpRecyclerView(view);
        navController = NavHostFragment.findNavController(this);
        backButton = view.findViewById(R.id.backButtonToConfirmOrder);
        backButton.setOnClickListener(v -> handleBackPressed());
        applyButton = view.findViewById(R.id.applyButton);
        branchViewModel.fetchBranches(1, 15, SortType.DESC, OrderSortBy.CREATED_AT);
        View applyButton = view.findViewById(R.id.applyButton);
        applyButton.setOnClickListener(v -> {
            BranchModel selectedBranch = branchAdapter.getSelectedBranch();
            if (selectedBranch != null) {
                Bundle result = new Bundle();
                result.putSerializable("selectedBranch", selectedBranch);
                getParentFragmentManager().setFragmentResult("branchResult", result);
                navController.popBackStack();
            }
        });

        branchViewModel.getBranchModelsLiveData().observe(getViewLifecycleOwner(), branchList -> {
            if (branchList != null) {
                branchAdapter.updateList(branchList);
            }
        });
    }

    private void setUpRecyclerView(View view) {
        branchRecyclerView = view.findViewById(R.id.branchRecyclerView);
        branchAdapter = new ChooseBranchAdapter(new ArrayList<>(), branch -> {
            // Handle branch selection here (e.g., navigate back with result)
        });
        branchRecyclerView.setAdapter(branchAdapter);
        branchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        int marginTop = getResources().getDimensionPixelOffset(R.dimen.vertical_spacing);
        branchRecyclerView.addItemDecoration(new SpaceItemDecoration().setTop(marginTop));
    }

    public ImageButton getBackButton() {
        return backButton;
    }

    public BranchModel getSelectedBranch() {
        return selectedBranch;
    }

    private void handleBackPressed() {
        navController.popBackStack();
    }

    private void applyBranch() {
        Bundle result = new Bundle();
        if (selectedBranch != null) {
            result.putSerializable("selectedBranch", selectedBranch);
        } else {
            result.putString("noBranch", "true");
        }
        getParentFragmentManager().setFragmentResult("branchResult", result);
        handleBackPressed();
    }

    public Button getApplyButton() {
        return applyButton;
    }

    public void setApplyButton(Button applyButton) {
        this.applyButton = applyButton;
    }

    public void setSelectedBranch(BranchModel selectedBranch) {
        this.selectedBranch = selectedBranch;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).hideBottomNavigation();
        }
    }

    @Override
    public void onPause() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showBottomNavigation();
        }
        super.onPause();
    }
}