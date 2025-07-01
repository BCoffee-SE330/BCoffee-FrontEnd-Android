package com.example.coffeeshopmanagementandroid.ui.fragment.discount;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.data.dto.discount.response.LiteProductResponse;
import com.example.coffeeshopmanagementandroid.domain.model.DiscountModel;
import com.example.coffeeshopmanagementandroid.domain.model.product.ProductModel;
import com.example.coffeeshopmanagementandroid.ui.MainActivity;
import com.example.coffeeshopmanagementandroid.ui.adapter.ProductAdapter;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.DiscountViewModel;
import com.example.coffeeshopmanagementandroid.utils.NavigationUtils;
import com.example.coffeeshopmanagementandroid.utils.helper.CurrencyFormat;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DiscountDetailFragment extends Fragment {
    private DiscountViewModel discountViewModel;
    private ImageButton backButton;
    private ProductAdapter productAdapter;
    private RecyclerView productRecyclerView;
    private NavController navController;

    private TextView tvDiscountName, tvDiscountDescription, tvDiscountType, tvDiscountValue,
            tvDiscountCode, tvDiscountStartDate, tvDiscountEndDate, tvDiscountMaxUses,
            tvDiscountMaxPerUser, tvDiscountMinOrderValue, tvBranchName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_discount, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        discountViewModel = new ViewModelProvider(this).get(DiscountViewModel.class);
        navController = NavHostFragment.findNavController(this);

        // Init views
        backButton = view.findViewById(R.id.backButtonDetailDiscount);
        productRecyclerView = view.findViewById(R.id.appliedProductRecyclerView);

        tvDiscountName = view.findViewById(R.id.tvDiscountName);
        tvDiscountDescription = view.findViewById(R.id.tvDiscountDescription);
        tvDiscountType = view.findViewById(R.id.tvDiscountType);
        tvDiscountValue = view.findViewById(R.id.tvDiscountValue);
        tvDiscountCode = view.findViewById(R.id.tvDiscountCode);
        tvDiscountStartDate = view.findViewById(R.id.tvDiscountStartDate);
        tvDiscountEndDate = view.findViewById(R.id.tvDiscountEndDate);
        tvDiscountMaxUses = view.findViewById(R.id.tvDiscountMaxUses);
        tvDiscountMaxPerUser = view.findViewById(R.id.tvDiscountMaxPerUser);
        tvDiscountMinOrderValue = view.findViewById(R.id.tvDiscountMinOrderValue);
        tvBranchName = view.findViewById(R.id.tvBranchName);

        // Back button
        backButton.setOnClickListener(v -> navController.popBackStack());

        // Get discountId from arguments
        String discountId;
        if (getArguments() != null) {
            discountId = getArguments().getString("discountId");
        } else {
            discountId = null;
        }

        if (discountId != null) {
            discountViewModel.fetchDiscountById(discountId);
        }

        // Observe discount data
        discountViewModel.getDiscountsLiveData().observe(getViewLifecycleOwner(), discounts -> {
            if (discounts != null && !discounts.isEmpty()) {
                // Find the correct discount by id
                DiscountModel discount = null;
                for (DiscountModel d : discounts) {
                    if (discountId.equals(d.getDiscountId())) {
                        discount = d;
                        break;
                    }
                }
                if (discount != null) {
                    bindDiscountData(discount);
                }
            }
        });
    }

    private void bindDiscountData(DiscountModel discount) {
        tvDiscountName.setText(nonNull(discount.getDiscountName()));
        tvDiscountDescription.setText(nonNull(discount.getDiscountDescription()));
        tvDiscountType.setText(nonNull(discount.getDiscountType()));
        tvDiscountValue.setText(discount.getDiscountValue() != null ? CurrencyFormat.formatVND(discount.getDiscountValue()) : "-");
        tvDiscountCode.setText(nonNull(discount.getDiscountCode()));

        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tvDiscountStartDate.setText(discount.getDiscountStartDate() != null ? discount.getDiscountStartDate().format(formatter) : "-");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tvDiscountEndDate.setText(discount.getDiscountEndDate() != null ? discount.getDiscountEndDate().format(formatter) : "-");
        }

        tvDiscountMaxUses.setText(String.valueOf(discount.getDiscountMaxUses()));
        tvDiscountMaxPerUser.setText(String.valueOf(discount.getDiscountMaxPerUser()));
        tvDiscountMinOrderValue.setText(discount.getDiscountMinOrderValue() != null ? CurrencyFormat.formatVND(discount.getDiscountMinOrderValue()) : "-");
        tvBranchName.setText(nonNull(discount.getDiscountBranchName()));

        // Hiển thị danh sách sản phẩm áp dụng
        List<LiteProductResponse> liteProducts = discount.getProducts();
        List<ProductModel> productModels = new ArrayList<>();
        if (liteProducts != null) {
            for (LiteProductResponse lite : liteProducts) {
                ProductModel p = new ProductModel();
                p.setProductId(lite.getId());
                p.setProductName(lite.getName());
                p.setProductThumb(lite.getThumb());
                p.setProductDescription("");
                p.setProductPrice(BigDecimal.valueOf(Double.parseDouble(lite.getPrice())));
                p.setProductRatingsAverage(BigDecimal.valueOf(Double.parseDouble(lite.getRatingAverage())));
                productModels.add(p);
            }
        }
        productAdapter = new ProductAdapter(productModels,
                product -> {
                    Bundle args = new Bundle();
                    args.putString("productId", product.getProductId());
                    NavigationUtils.safeNavigate(
                            navController,
                            R.id.detail_discount_fragment,
                            R.id.action_detail_discount_fragment_to_detailProductFragment,
                            "DetailProductFragment",
                            "DiscountDetailFragment",
                            args
                    );
                },
                product -> {
                    Bundle args = new Bundle();
                    args.putString("productId", product.getProductId());
                    NavigationUtils.safeNavigate(
                            navController,
                            R.id.detail_discount_fragment,
                            R.id.action_detail_discount_fragment_to_detailProductFragment,
                            "DetailProductFragment",
                            "DiscountDetailFragment",
                            args
                    );
                }
        );
        productRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        productRecyclerView.setAdapter(productAdapter);
    }

    private String nonNull(String s) {
        return s != null && !s.isEmpty() ? s : "-";
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