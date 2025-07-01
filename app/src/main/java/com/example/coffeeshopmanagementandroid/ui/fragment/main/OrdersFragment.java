package com.example.coffeeshopmanagementandroid.ui.fragment.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.OrderModel;
import com.example.coffeeshopmanagementandroid.domain.model.OrderStatusModel;
import com.example.coffeeshopmanagementandroid.ui.adapter.OrderAdapter;
import com.example.coffeeshopmanagementandroid.ui.adapter.StatusAdapter;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.OrderViewModel;
import com.example.coffeeshopmanagementandroid.utils.NavigationUtils;
import com.example.coffeeshopmanagementandroid.utils.SharedPreferencesUtils;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.OrderSortBy;
import com.example.coffeeshopmanagementandroid.utils.enums.OrderStatus;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrdersFragment extends Fragment {
    private OrderAdapter orderAdapter;
    private StatusAdapter orderStatusAdapter;
    private RecyclerView orderRecyclerView;
    private RecyclerView orderStatusRecyclerView;
    private OrderViewModel orderViewModel;
    private String customerId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);

        orderStatusRecyclerView = view.findViewById(R.id.orderStatusRecyclerView);
        orderRecyclerView = view.findViewById(R.id.orderRecyclerView);

        customerId = SharedPreferencesUtils.getUserId(requireContext());

        setupRecyclerView();
        fetchAndObserveOrders();

        // Thiết lập listener cho status
        orderStatusAdapter.setOnStatusSelectedListener(statusName -> filterOrdersByStatusOrder(statusName));
    }

    private void setupRecyclerView() {
        // Status order
        List<OrderStatusModel> statuses = new ArrayList<>();
        for (OrderStatus orderStatus : OrderStatus.values()) {
            statuses.add(new OrderStatusModel(orderStatus.getStatus()));
        }
        orderStatusRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        orderStatusAdapter = new StatusAdapter(statuses);
        orderStatusRecyclerView.setAdapter(orderStatusAdapter);

        // Order
        List<OrderModel> orders = new ArrayList<>();
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        orderAdapter = new OrderAdapter(orders, order -> {
            NavController navController = Navigation.findNavController(requireView());
            Bundle args = new Bundle();
            args.putString("orderId", order.getOrderId());
            NavigationUtils.safeNavigate(navController, R.id.orderFragment, R.id.action_orderFragment_to_detailOrderFragment, "DetailOrderFragment", "OrderFragment", args);
        });
        orderRecyclerView.setAdapter(orderAdapter);
        orderRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Boolean.FALSE.equals(orderViewModel.getIsLoading().getValue()) && dy > 0 && Boolean.FALSE.equals(orderViewModel.getIsAllDataLoaded().getValue())) {
                    LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (lm != null) {
                        int totalItemCount = lm.getItemCount();
                        int lastVisibleItem = lm.findLastVisibleItemPosition();
                        if (totalItemCount > 0 && lastVisibleItem >= totalItemCount - 5) {
                            orderViewModel.fetchMoreOrdersCustomer(SortType.DESC, OrderSortBy.CREATED_AT);
                        }
                    }
                }
            }
        });
    }

    private void fetchAndObserveOrders() {
        if (customerId != null) {
            orderViewModel.fetchAllOrdersCustomer( 1, 15, SortType.DESC, OrderSortBy.CREATED_AT);
        } else {
            Toast.makeText(getContext(), "Can not fetch ORDERS", Toast.LENGTH_SHORT).show();
            return;
        }

        orderViewModel.getOrdersLiveData().observe(getViewLifecycleOwner(), orders -> {
            if (orders != null) {
                orderAdapter.setOrders(orders);
            } else {
                orderAdapter.setOrders(new ArrayList<>());
            }
        });


        orderViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {

        });

        orderViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        orderViewModel.getIsAllDataLoaded().observe(getViewLifecycleOwner(), isAllLoaded -> {
            if (isAllLoaded) {
                Log.d("OrdersFragment", "All data loaded, no more fetching");
            }
        });
    }

    private void filterOrdersByStatusOrder(String statusName) {
        if (statusName == null) {
            // Reset về danh sách đầy đủ
            orderAdapter.setOrders(orderViewModel.getOrdersLiveData().getValue());
        } else {
            orderViewModel.getOrdersLiveData().observe(getViewLifecycleOwner(), orders -> {
                if (orders != null) {
                    List<OrderModel> filteredOrders = new ArrayList<>();
                    for (OrderModel order : orders) {
                        if (order.getOrderStatus().equals(statusName)) {
                            Log.d("OrderStatus", order.getOrderStatus());
                            Log.d("statusName", statusName);
                            Log.d("Answer", (order.getOrderStatus() != null && order.getOrderStatus().equals(statusName)) ? "true" : "false");
                            filteredOrders.add(order);
                        }
                    }
                    orderAdapter.setOrders(filteredOrders);
                }
            });

        }
    }
}