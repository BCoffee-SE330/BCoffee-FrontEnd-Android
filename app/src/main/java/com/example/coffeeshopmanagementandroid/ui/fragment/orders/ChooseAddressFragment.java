package com.example.coffeeshopmanagementandroid.ui.fragment.orders;

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

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.address.AddressModel;
import com.example.coffeeshopmanagementandroid.domain.model.payment.PaymentMethodModel;
import com.example.coffeeshopmanagementandroid.ui.MainActivity;
import com.example.coffeeshopmanagementandroid.ui.adapter.ChooseAddressAdapter;
import com.example.coffeeshopmanagementandroid.ui.adapter.OrderProductAdapter;
import com.example.coffeeshopmanagementandroid.ui.adapter.PaymentMethodAdapter;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.ConfirmOrderViewModel;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.CartSortBy;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChooseAddressFragment extends Fragment {
    private ConfirmOrderViewModel confirmOrderViewModel;
    private RecyclerView addressRecyclerView;
    private ChooseAddressAdapter chooseAddressAdapter;
    private ImageButton backButton;
    private NavController navController;
    private AddressModel selectedAddress;

    public ChooseAddressFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        confirmOrderViewModel = new ViewModelProvider(requireActivity()).get(ConfirmOrderViewModel.class);
        initViews(view);
        setupAddress();

    }

    private void initViews(View view) {
        navController = NavHostFragment.findNavController(this);
        backButton = view.findViewById(R.id.backButtonToConfirmOrder);
        addressRecyclerView = view.findViewById(R.id.addressRecyclerView);

        backButton.setOnClickListener(v -> handleBackPressed());
    }

    private void handleBackPressed() {
        if (getView() != null) {
            Navigation.findNavController(getView()).popBackStack();
        }
    }

    private void setupAddress() {
        addressRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        confirmOrderViewModel.getAddressesLiveData().observe(getViewLifecycleOwner(), addresses -> {
            if (addresses != null && !addresses.isEmpty()) {
                AddressModel selected = confirmOrderViewModel.getSelectedAddress().getValue();
                int selectedIndex = -1;
                if (selected != null) {
                    for (int i = 0; i < addresses.size(); i++) {
                        if (addresses.get(i).getAddressId().equals(selected.getAddressId())) {
                            selectedIndex = i;
                            break;
                        }
                    }
                }

                chooseAddressAdapter = new ChooseAddressAdapter(
                        addresses,
                        selectedIndex,
                        method -> {
                            selectedAddress = method;
                            confirmOrderViewModel.getSelectedAddress().setValue(method); // cập nhật ViewModel
                        }
                );

                addressRecyclerView.setAdapter(chooseAddressAdapter);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).hideBottomNavigation();
        }
    }
}