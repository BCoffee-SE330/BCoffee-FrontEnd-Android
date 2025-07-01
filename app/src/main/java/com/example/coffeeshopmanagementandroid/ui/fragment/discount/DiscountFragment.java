package com.example.coffeeshopmanagementandroid.ui.fragment.discount;

import android.os.Bundle;
import android.os.Parcelable;
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
import com.example.coffeeshopmanagementandroid.domain.model.DiscountModel;
import com.example.coffeeshopmanagementandroid.ui.MainActivity;
import com.example.coffeeshopmanagementandroid.ui.adapter.DiscountAdapter;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.CartViewModel;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.DiscountViewModel;
import com.example.coffeeshopmanagementandroid.utils.NavigationUtils;
import com.example.coffeeshopmanagementandroid.utils.SpaceItemDecoration;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.DiscountSortBy;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DiscountFragment extends Fragment {
    private DiscountViewModel discountViewModel;
    private RecyclerView discountRecyclerView;
    private NavController navController;
    private ImageButton backButton;
    private DiscountAdapter discountAdapter;
    private DiscountModel selectedDiscount;
    private List<String> appliedDiscountIds = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discount, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        discountViewModel = new ViewModelProvider(this).get(DiscountViewModel.class);
        initViews(view);
        setupDiscounts();
    }

    private void initViews(View view) {
        navController = NavHostFragment.findNavController(this);
        discountRecyclerView = view.findViewById(R.id.discountRecyclerView);
        backButton = view.findViewById(R.id.backButtonToConfirmOrder);

        backButton.setOnClickListener(v -> handleBackPressed());
    }

    private void setupDiscounts() {
        List<String> discountIds = new ArrayList<>();
        Bundle args = getArguments();
        if (args != null && args.containsKey("discountIds")) {
            discountIds = args.getStringArrayList("discountIds");
        }

        List<String> finalDiscountIds = discountIds;
        new Thread(() -> {
            try {
                discountViewModel.FetchDiscountsByIdIn(finalDiscountIds, 1, 20, SortType.DESC, DiscountSortBy.CREATED_AT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        discountAdapter = new DiscountAdapter(
                new ArrayList<>(),
                discount -> {
                    this.selectedDiscount = discount;
                    Bundle navArgs = new Bundle();
                    navArgs.putString("discountId", discount.getDiscountId());
                    NavigationUtils.safeNavigate(
                            navController,
                            R.id.discountFragment,
                            R.id.action_discountFragment_to_detail_discount_fragment,
                            "DiscountDetailFragment",
                            "DiscountFragment",
                            navArgs
                    );
                }
        );

        discountRecyclerView.setAdapter(discountAdapter);
        discountRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        int marginTop = getResources().getDimensionPixelOffset(R.dimen.vertical_spacing);
        discountRecyclerView.addItemDecoration(new SpaceItemDecoration().setTop(marginTop));

        discountViewModel.getDiscountsLiveData().observe(getViewLifecycleOwner(), discounts -> {
            discountAdapter.setDiscounts(discounts != null ? discounts : new ArrayList<>());
        });

        discountRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        discountRecyclerView.addItemDecoration(new SpaceItemDecoration().setTop(marginTop));

    }

    private void handleBackPressed() {
        navController.popBackStack();
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