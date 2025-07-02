package com.example.coffeeshopmanagementandroid.ui.fragment.other.address;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.address.AddressModel;
import com.example.coffeeshopmanagementandroid.ui.adapter.AddressAdapter;
import com.example.coffeeshopmanagementandroid.ui.fragment.other.BaseOtherFragment;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.ConfirmOrderViewModel;
import com.example.coffeeshopmanagementandroid.utils.NavigationUtils;
import com.example.coffeeshopmanagementandroid.utils.SpaceItemDecoration;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class AddressFragment extends BaseOtherFragment {

    private RecyclerView addressRecyclerView;
    private MaterialButton btnAddAddress;
    private NavController navController;
    private ConfirmOrderViewModel confirmOrderViewModel;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_address;
    }

    @Override
    protected void initViews(View view) {
        confirmOrderViewModel = new ViewModelProvider(this).get(ConfirmOrderViewModel.class);
        confirmOrderViewModel.fetchAllAddresses();
        setUpRecyclerView(view);
        observeAddresses();
        setupRecentlyAddress(view);
        navController = NavHostFragment.findNavController(this);
        NavigationUtils.checkAndFixNavState(navController, R.id.addressFragment, "AddressFragment");
        btnAddAddress = view.findViewById(R.id.btnAddAddress);
        btnAddAddress.setOnClickListener(v -> {
            navigateToAddAddressFragment();
        });
    }

    private void observeAddresses() {
        confirmOrderViewModel.getAddressesLiveData().observe(getViewLifecycleOwner(), addresses -> {
            if (addresses != null && addressRecyclerView.getAdapter() != null) {
                ((AddressAdapter) addressRecyclerView.getAdapter()).updateList(addresses);
            }
        });
    }

    private void setUpRecyclerView(View view) {
        addressRecyclerView = view.findViewById(R.id.addressRecyclerView);
        AddressAdapter adapter = new AddressAdapter(new ArrayList<>(), this::navigateToUpdateAddress);
        addressRecyclerView.setAdapter(adapter);
        addressRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        int marginTop = getResources().getDimensionPixelOffset(R.dimen.vertical_spacing);
        addressRecyclerView.addItemDecoration(new SpaceItemDecoration().setTop(marginTop));
    }

    private void setupRecentlyAddress(View view) {
        View recentlyAddress = view.findViewById(R.id.iRecentlyAddress);
        if (recentlyAddress != null) {
            TextView tvAddress = recentlyAddress.findViewById(R.id.tvRecentlyAddress);
            ImageView iconEditAddress = recentlyAddress.findViewById(R.id.iconEditRecentlyAddress);

            // Get addresses from view model instead of creating a new list
            List<AddressModel> addresses = confirmOrderViewModel.getAddressesLiveData().getValue();

            if (addresses != null && !addresses.isEmpty()) {
                AddressModel recentAddress = addresses.get(0);
                tvAddress.setText(recentAddress.getAddressLine() + "\n" + recentAddress.getAddressDistrict() + ", " + recentAddress.getAddressCity());
                recentlyAddress.setOnClickListener(v -> navigateToUpdateAddress(recentAddress));
            } else {
                tvAddress.setText("Không có địa chỉ gần đây");
                recentlyAddress.setVisibility(View.GONE);
            }
        }
    }

    private void navigateToUpdateAddress(AddressModel address) {
        if (address != null) {
            Bundle args = new Bundle();
            args.putString("addressId", address.getAddressId().toString());
            args.putString("addressLine", address.getAddressLine());
            args.putString("addressDistrict", address.getAddressDistrict());
            args.putString("addressCity", address.getAddressCity());
            args.putBoolean("isDefault", address.getAddressIsDefault());

            NavigationUtils.safeNavigate(navController,
                    R.id.addressFragment,
                    R.id.action_addressFragment_to_updateAddressFragment,
                    "UpdateAddressFragment",
                    "AddressFragment",
                    args);
        } else {
            Log.e("AddressFragment", "Cannot navigate: address is null");
        }
    }

    private void navigateToAddAddressFragment() {
        NavigationUtils.safeNavigate(navController,
                R.id.addressFragment,
                R.id.action_addressFragment_to_addAddressFragment,
                "AddAddressFragment",
                "AddressFragment",
                null);
    }
}