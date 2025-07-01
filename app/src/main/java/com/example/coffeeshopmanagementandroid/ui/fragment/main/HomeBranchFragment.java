package com.example.coffeeshopmanagementandroid.ui.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.ui.adapter.BranchAdapter;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.BranchViewModel;
import com.example.coffeeshopmanagementandroid.utils.NavigationUtils;
import com.example.coffeeshopmanagementandroid.utils.SpaceItemDecoration;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.OrderSortBy;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeBranchFragment extends Fragment {

    private BranchViewModel branchViewModel;
    private RecyclerView branchRecyclerView;
    private BranchAdapter branchAdapter;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_branch_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        branchViewModel = new ViewModelProvider(this).get(BranchViewModel.class);

        initViews(view);
        setupRecyclerView();
        fetchAndObserveBranches();
    }

    private void initViews(View view) {
        navController = NavHostFragment.findNavController(this);
        branchRecyclerView = view.findViewById(R.id.branchRecyclerView);
    }

    private void setupRecyclerView() {
        branchAdapter = new BranchAdapter(
                new ArrayList<>(),
                branch -> {
                    Bundle args = new Bundle();
                    args.putString("branchId", branch.getId());
                    NavigationUtils.safeNavigate(
                            navController,
                            R.id.home_branch_fragment,
                            R.id.action_home_branch_fragment_to_branch_detail_fragment,
                            "BranchDetailFragment",
                            "HomeBranchFragment",
                            args
                    );
                }
        );

        branchRecyclerView.setAdapter(branchAdapter);
        branchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        int marginVertical = getResources().getDimensionPixelOffset(R.dimen.vertical_spacing);
        branchRecyclerView.addItemDecoration(new SpaceItemDecoration().setTop(marginVertical));
    }

    private void fetchAndObserveBranches() {
        branchViewModel.fetchBranches(1, 20, SortType.DESC, OrderSortBy.CREATED_AT);

        branchViewModel.getBranchModelsLiveData().observe(getViewLifecycleOwner(), branches -> {
            if (branches != null) {
                branchAdapter.setBranches(branches);
            } else {
                branchAdapter.setBranches(new ArrayList<>());
                Toast.makeText(requireContext(), "No branches available", Toast.LENGTH_SHORT).show();
            }
        });
    }
}