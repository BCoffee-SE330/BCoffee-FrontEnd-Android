package com.example.coffeeshopmanagementandroid.ui.fragment.orders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.DiscountModel;
import com.example.coffeeshopmanagementandroid.domain.model.address.AddressModel;
import com.example.coffeeshopmanagementandroid.domain.model.branch.BranchModel;
import com.example.coffeeshopmanagementandroid.domain.model.payment.PaymentMethodModel;
import com.example.coffeeshopmanagementandroid.ui.MainActivity;
import com.example.coffeeshopmanagementandroid.ui.adapter.ChooseBranchAdapter;
import com.example.coffeeshopmanagementandroid.ui.adapter.OrderProductAdapter;
import com.example.coffeeshopmanagementandroid.ui.adapter.PaymentMethodAdapter;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.ConfirmOrderViewModel;
import com.example.coffeeshopmanagementandroid.utils.NavigationUtils;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.CartSortBy;
import com.example.coffeeshopmanagementandroid.utils.helper.CurrencyFormat;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ConfirmOrderFragment extends Fragment {
    private ConfirmOrderViewModel confirmOrderViewModel;
    private OrderProductAdapter orderProductAdapter;
    private RecyclerView orderProductRecyclerView;
    private PaymentMethodAdapter paymentMethodAdapter;
    private RecyclerView paymentMethodRecyclerView;
    private RecyclerView branchRecyclerView;
    private PaymentMethodModel selectedPaymentMethod;
    private NavController navController;
    private View applyDiscountLayout;
    private View chooseAddressLayout;
    private ImageButton backButton;
    private MaterialButton buyButton;
    private TextView totalPrice;
    private TextView totalDiscountCost;
    private TextView totalCostAfterDiscount;
    private List<String> appliedDiscountIds;
    private TextView tvAddress;
    private DiscountModel selectedDiscount;
    private ChooseBranchAdapter chooseBranchAdapter;
    private BranchModel selectedBranch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirm_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        confirmOrderViewModel = new ViewModelProvider(requireActivity()).get(ConfirmOrderViewModel.class);
        initViews(view);
        setupOrderProducts();
        setupPaymentMethods();
        setupAddress();
        setupBranch();
        getParentFragmentManager().setFragmentResultListener("discountResult", this, (requestKey, result) -> {
            if (result.containsKey("selectedDiscount")) {
                selectedDiscount = result.getParcelable("selectedDiscount");
                // Cập nhật giao diện hoặc logic với selectedDiscount, ví dụ: hiển thị tên mã giảm giá
            } else {
                selectedDiscount = null;
                // Xử lý trường hợp không chọn mã giảm giá
            }
        });
        confirmOrderViewModel.getApprovalLinkLiveData().observe(getViewLifecycleOwner(), link -> {
            if (link != null) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(browserIntent);
            }
        });
        getParentFragmentManager().setFragmentResultListener("branchResult", this, (requestKey, result) -> {
            if (result.containsKey("selectedBranch")) {
                selectedBranch = (BranchModel) result.getSerializable("selectedBranch");
                confirmOrderViewModel.getSelectedBranch().setValue(selectedBranch);
                confirmOrderViewModel.applyDiscountToCart(selectedBranch.getId());
            } else {
                confirmOrderViewModel.getSelectedBranch().setValue(null);
                selectedBranch = null;
            }
        });
        confirmOrderViewModel.getTotalPrice().observe(getViewLifecycleOwner(), price -> {
            if (price != null) {
                totalPrice.setText(CurrencyFormat.formatVND(price));
            }
        });
        confirmOrderViewModel.getTotalDiscountCost().observe(getViewLifecycleOwner(), discount -> {
            if (discount != null) {
                totalDiscountCost.setText(CurrencyFormat.formatVND(discount));
            }
        });
        confirmOrderViewModel.getTotalCostAfterDiscount().observe(getViewLifecycleOwner(), total -> {
            if (total != null) {
                totalCostAfterDiscount.setText(CurrencyFormat.formatVND(total));
            }
        });
        confirmOrderViewModel.getAppliedDiscountIds().observe(getViewLifecycleOwner(), discountIds -> {
            if (discountIds != null) {
                appliedDiscountIds = discountIds;
            } else {
                appliedDiscountIds = new ArrayList<>();
            }
        });
    }

    private void initViews(View view) {
        navController = NavHostFragment.findNavController(this);
        applyDiscountLayout = view.findViewById(R.id.applyDiscountLayout);
        chooseAddressLayout = view.findViewById(R.id.chooseAddressLayout);
        orderProductRecyclerView = view.findViewById(R.id.orderProductRecyclerView);
        paymentMethodRecyclerView = view.findViewById(R.id.paymentMethodRecyclerView);
        branchRecyclerView = view.findViewById(R.id.branchRecyclerView);
        backButton = view.findViewById(R.id.backButtonToCart);
        buyButton = view.findViewById(R.id.buyButton);
        totalPrice = view.findViewById(R.id.totalProductsOrder);
        tvAddress = view.findViewById(R.id.tvAddress);
        totalDiscountCost = view.findViewById(R.id.feeShipping);
        totalCostAfterDiscount = view.findViewById(R.id.totalPrice);

        // Set Listener
        backButton.setOnClickListener(v -> handleBackPressed());
        buyButton.setOnClickListener(v -> handleBuyNow());

        // Xử lý bấm vào applyDiscountLayout
        applyDiscountLayout.setOnClickListener(v -> navigateToDiscountFragment());
        chooseAddressLayout.setOnClickListener(v -> navigateToChooseAddressFragment());
    }

    private void navigateToDiscountFragment() {

        if (appliedDiscountIds == null || appliedDiscountIds.isEmpty()) {
            Toast.makeText(requireContext(), "Chưa có mã giảm giá nào được áp dụng", Toast.LENGTH_SHORT).show();
            return;
        }

        Bundle args = new Bundle();
        args.putStringArrayList("discountIds", new ArrayList<>(appliedDiscountIds));
        NavigationUtils.safeNavigate(
                navController,
                R.id.confirmOrderFragment,
                R.id.action_confirmOrderFragment_to_discountFragment,
                "DiscountFragment",
                "ConfirmOrderFragment",
                args
        );
    }

    private void navigateToChooseAddressFragment() {
        NavigationUtils.safeNavigate(navController,
                R.id.confirmOrderFragment,
                R.id.action_cartFragment_to_chooseAddressFragment,
                "ChooseAddressFragment",
                "ConfirmOrderFragment",
                null);
    }

    private void navigateToOrderFragment() {
        NavigationUtils.safeNavigate(navController,
                R.id.confirmOrderFragment,
                R.id.action_confirmOrderFragment_to_orderFragment,
                "OrderFragment",
                "ConfirmOrderFragment",
                null);
    }

    private void setupOrderProducts() {
        confirmOrderViewModel.fetchAllCartItems(1, 10, SortType.DESC, CartSortBy.CREATED_AT);

        confirmOrderViewModel.getCartItemsLiveData().observe(getViewLifecycleOwner(), cartItems -> {
            orderProductAdapter.updateList(cartItems);
        });

        confirmOrderViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            // Show/hide loading if needed
        });

        confirmOrderViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        confirmOrderViewModel.getTotalPrice().observe(getViewLifecycleOwner(), price -> {
            if (price != null) {
                totalPrice.setText("Total Price: " + price + " VND");
            }
        });

        orderProductRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        orderProductAdapter = new OrderProductAdapter(new ArrayList<>());
        orderProductRecyclerView.setAdapter(orderProductAdapter);
    }

    private void setupPaymentMethods() {
        paymentMethodAdapter = new PaymentMethodAdapter(
                new ArrayList<>(), // khởi tạo danh sách trống ban đầu
                -1, // chưa chọn mặc định
                method -> selectedPaymentMethod = method // callback khi chọn
        );

        paymentMethodRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        paymentMethodRecyclerView.setAdapter(paymentMethodAdapter);

        confirmOrderViewModel.fetchAllPaymentMethods();
        confirmOrderViewModel.getPaymentMethodsLiveData().observe(getViewLifecycleOwner(), paymentMethods -> {
            paymentMethodAdapter.updateList(paymentMethods);
        });
    }

    private void setupAddress() {
        if (confirmOrderViewModel.getSelectedAddress().getValue() == null) {
            confirmOrderViewModel.fetchAllAddresses();
        }
        confirmOrderViewModel.getSelectedAddress().observe(getViewLifecycleOwner(), address -> {
            if (address != null) {
                tvAddress.setText("Địa chỉ: " + address.getAddressLine());
            } else {
                tvAddress.setText("Chưa chọn địa chỉ");
            }
        });
    }

    private void setupBranch() {
        View chooseBranchLayout = getView().findViewById(R.id.chooseBranchLayout);
        chooseBranchLayout.setOnClickListener(v -> {
            NavigationUtils.safeNavigate(
                    navController,
                    R.id.confirmOrderFragment,
                    R.id.action_confirmOrderFragment_to_branchFragment,
                    "BranchFragment",
                    "ConfirmOrderFragment",
                    null
            );
        });

        confirmOrderViewModel.getSelectedBranch().observe(getViewLifecycleOwner(), branch -> {
            TextView tvBranch = getView().findViewById(R.id.tvBranch);
            if (branch != null) {
                tvBranch.setText(branch.getBranchName());
            } else {
                tvBranch.setText("Chưa chọn chi nhánh");
            }
        });
    }

    private void handleBackPressed() {
        if (getView() != null) {
            Navigation.findNavController(getView()).popBackStack();
        }
    }

    private void handleBuyNow() {

        // Check null
        if (selectedPaymentMethod == null) {
            Toast.makeText(requireContext(), "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedBranch == null) {
            Toast.makeText(requireContext(), "Vui lòng chọn chi nhánh", Toast.LENGTH_SHORT).show();
            return;
        }
        if (confirmOrderViewModel.getSelectedAddress().getValue() == null) {
            Toast.makeText(requireContext(), "Vui lòng chọn địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy thông tin từ các ViewModel và Adapter
        PaymentMethodModel selectedPaymentMethods = paymentMethodAdapter.getSelectedPaymentMethod();
        AddressModel address = confirmOrderViewModel.getSelectedAddress().getValue() != null ? confirmOrderViewModel.getSelectedAddress().getValue() : null;

        // Gọi ViewModel để tạo đơn hàng
        assert address != null;
        confirmOrderViewModel.createOrder(address.getAddressId() ,selectedPaymentMethods.getPaymentMethodId(), selectedBranch.getId());
        Toast.makeText(requireContext(), "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
        navigateToOrderFragment();
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
