package com.example.coffeeshopmanagementandroid.ui.fragment.orders;

import static com.example.coffeeshopmanagementandroid.data.mapper.OrderMapper.mapOrderDetailResponsesToCartItems;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.OrderItemModel;
import com.example.coffeeshopmanagementandroid.domain.model.cart.CartItemModel;
import com.example.coffeeshopmanagementandroid.ui.MainActivity;
import com.example.coffeeshopmanagementandroid.ui.adapter.OrderProductAdapter;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.DetailOrderViewModel;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.DetailProductViewModel;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.CartSortBy;
import com.example.coffeeshopmanagementandroid.utils.helper.CurrencyFormat;
import com.example.coffeeshopmanagementandroid.utils.helper.StatusFormat;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailOrderFragment extends Fragment {
    private OrderProductAdapter orderProductAdapter;
    private RecyclerView orderProductRecyclerView;

    private DetailOrderViewModel detailOrderViewModel;
    private TextView tvShippingAddress;
    private TextView customerNameTextView;
    private TextView customerPhoneTextView;
    private TextView statusOrderTextView;
    private TextView tvTotalOrder;
    private TextView tvOriginalTotal;
    private TextView tvDiscount;
    private Button cancelOrderButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout cho Fragment
        View view = inflater.inflate(R.layout.fragment_detail_order, container, false);

        ImageButton backButton = view.findViewById(R.id.backButtonOrder);
        backButton.setOnClickListener(v -> {
            handleBackPressed();
        });

        cancelOrderButton = view.findViewById(R.id.btnCancelOrder);
        cancelOrderButton.setOnClickListener(v -> {
            handleCancelOrder();
        });

        setupRecyclerView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize ViewModel
        detailOrderViewModel = new ViewModelProvider(this).get(DetailOrderViewModel.class);
        String orderId = requireArguments().getString("orderId");

        Log.d("DetailProductFragment", "Received productId: " + orderId);
        if (orderId != null) {
            detailOrderViewModel.fetchOrderDetail(orderId);
        } else {
            Toast.makeText(requireContext(), "Missing product ID", Toast.LENGTH_SHORT).show();
            return;
        }
        initView();
        setupOrderItems();
    }

    private void initView() {
        tvShippingAddress = requireView().findViewById(R.id.tvShippingAddress);
        customerNameTextView = requireView().findViewById(R.id.customerNameTextView);
        customerPhoneTextView = requireView().findViewById(R.id.customerPhoneTextView);
        statusOrderTextView = requireView().findViewById(R.id.statusOrderTextView);
        orderProductRecyclerView = requireView().findViewById(R.id.orderProductRecyclerView);

        tvOriginalTotal = requireView().findViewById(R.id.tvOriginalTotal);
        tvDiscount = requireView().findViewById(R.id.tvDiscount);
        tvTotalOrder = requireView().findViewById(R.id.tvTotalOrder);

        detailOrderViewModel.getAddress().observe(getViewLifecycleOwner(), address -> {
            if (address == null || address.trim().isEmpty()) {
                tvShippingAddress.setText("Tại quầy");
            } else {
                tvShippingAddress.setText(address);
            }
        });
        detailOrderViewModel.getUserName().observe(getViewLifecycleOwner(), userName -> {
            customerNameTextView.setText(userName);
        });
        detailOrderViewModel.getUserPhoneNumber().observe(getViewLifecycleOwner(), userPhoneNumber -> {
            customerPhoneTextView.setText(userPhoneNumber);
        });
        detailOrderViewModel.getOrderStatus().observe(getViewLifecycleOwner(), orderStatus -> {
            statusOrderTextView.setText(orderStatus);
            statusOrderTextView.setTextColor(StatusFormat.getColor(requireContext(), orderStatus));
        });

        detailOrderViewModel.getOrderTotalCost().observe(getViewLifecycleOwner(), originalTotal -> {
            tvOriginalTotal.setText(CurrencyFormat.formatVND(originalTotal));
        });

        detailOrderViewModel.getOrderDiscountCost().observe(getViewLifecycleOwner(), discount -> {
            tvDiscount.setText(CurrencyFormat.formatVND(discount));
        });

        detailOrderViewModel.getTotalPrice().observe(getViewLifecycleOwner(), totalPrice -> {
            tvTotalOrder.setText(CurrencyFormat.formatVND(totalPrice));
        });
    }

    private void setupOrderItems() {
        orderProductRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        orderProductAdapter = new OrderProductAdapter(new ArrayList<>());
        orderProductRecyclerView.setAdapter(orderProductAdapter);

        detailOrderViewModel.getOrderItems().observe(getViewLifecycleOwner(), cartItems -> {
            if (cartItems != null) {
                List<CartItemModel> list = mapOrderDetailResponsesToCartItems(cartItems);
                orderProductAdapter.updateList(list);
            }
        });
    }

    private void handleBackPressed() {
        if (getView() != null) {
            Navigation.findNavController(getView()).popBackStack();
        }
    }

    private void handleCancelOrder() {
        String orderId = requireArguments().getString("orderId");
        if (orderId != null) {
            detailOrderViewModel.cancelOrder(orderId);
            handleBackPressed();
        } else {
            Toast.makeText(requireContext(), "Cannot cancel order", Toast.LENGTH_SHORT).show();
        }
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

    private void setupRecyclerView(View view) {
        List<CartItemModel> products = new ArrayList<>();

        orderProductRecyclerView = view.findViewById(R.id.orderProductRecyclerView);
        orderProductRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        orderProductAdapter = new OrderProductAdapter(products);
        orderProductRecyclerView.setAdapter(orderProductAdapter);
    }
}