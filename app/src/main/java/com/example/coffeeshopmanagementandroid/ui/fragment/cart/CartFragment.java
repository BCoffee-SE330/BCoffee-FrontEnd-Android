package com.example.coffeeshopmanagementandroid.ui.fragment.cart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.cart.CartItemModel;
import com.example.coffeeshopmanagementandroid.ui.MainActivity;
import com.example.coffeeshopmanagementandroid.ui.adapter.ProductCartAdapter;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.CartViewModel;
import com.example.coffeeshopmanagementandroid.utils.NavigationUtils;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.CartSortBy;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.ProductSortBy;
import com.example.coffeeshopmanagementandroid.utils.helper.CurrencyFormat;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CartFragment extends Fragment {
    private CartViewModel cartViewModel;
    private ProductCartAdapter productCartAdapter;
    private Button buyButton;
    private ImageButton backButton;
    private NavController navController;
    private TextView totalPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout cho Fragment


        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        observeCartItems();
        initViews(view);
        setupProductCartRecyclerView(view);
    }

    void initViews(View view) {
        navController = NavHostFragment.findNavController(this);
        NavigationUtils.checkAndFixNavState(navController, R.id.cartFragment, "CartFragment");

        backButton = view.findViewById(R.id.backButtonToMain);
        buyButton = view.findViewById(R.id.buyButton);
        totalPrice = view.findViewById(R.id.totalPrice);

        backButton.setOnClickListener(v -> {
            // Xử lý khi nhấn nút back
            handleBackPressed();
        });
        buyButton.setOnClickListener(v -> navigateToConfirmOrderFragment());
    }

    void setupProductCartRecyclerView(View view) {
        productCartAdapter = new ProductCartAdapter(
                new ArrayList<>(), // ban đầu empty
                this::onMinusProduct,
                this::onDeleteProduct,
                this::onPlusProduct,
                this::onItemClick
        );

        RecyclerView productCardRecyclerView = view.findViewById(R.id.productCardRecyclerView);
        productCardRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        productCardRecyclerView.setAdapter(productCartAdapter);
    }

    private void onItemClick(CartItemModel product) {
        Bundle args = new Bundle();
        args.putString("productId", product.getProductId()); // Replace productId with your actual product id variable

        if (navController.getCurrentDestination() != null &&
                navController.getCurrentDestination().getId() == R.id.cartFragment) {
            navController.navigate(R.id.action_cartFragment_to_detailProductFragment, args);
        }
    }

    private void onMinusProduct(CartItemModel product) {
        if (product.getCartDetailQuantity() > 1) {
            cartViewModel.decrementCartItemWithDebounce(product);
        } else {
            Toast.makeText(requireContext(), "Số lượng sản phẩm không nhỏ hơn 1", Toast.LENGTH_SHORT).show();
        }
    }

    private void onPlusProduct(CartItemModel product) {
        cartViewModel.incrementCartItemWithDebounce(product);
    }

    private void onDeleteProduct(CartItemModel product) {
        cartViewModel.deleteCartItem(product);
        Toast.makeText(requireContext(),
                "Đã xoá " + product.getProductName() + " khỏi giỏ hàng",
                Toast.LENGTH_SHORT).show();
    }

    private void handleBackPressed() {
        if (getView() != null) {
            Navigation.findNavController(getView()).popBackStack();
        }
    }

    private void navigateToConfirmOrderFragment() {
        NavigationUtils.safeNavigate(navController,
                R.id.cartFragment,
                R.id.action_cartFragment_to_confirmOrderFragment,
                "ConfirmOrderFragment",
                "CartFragment",
                null);
    }

    private void observeCartItems() {
        cartViewModel.fetchAllCartItems(1, 10, SortType.DESC, CartSortBy.CREATED_AT, null);

        cartViewModel.getCartItemsLiveData().observe(getViewLifecycleOwner(), cartItems -> {
            productCartAdapter.updateList(cartItems);
        });

        cartViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            // Show/hide loading if needed
        });

        cartViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        cartViewModel.getTotalPrice().observe(getViewLifecycleOwner(), price -> {
            if (price != null) {
                totalPrice.setText(CurrencyFormat.formatVND(price));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
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