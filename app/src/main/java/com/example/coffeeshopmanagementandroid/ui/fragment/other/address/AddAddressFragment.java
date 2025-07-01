package com.example.coffeeshopmanagementandroid.ui.fragment.other.address;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.ui.MainActivity;
import com.example.coffeeshopmanagementandroid.ui.component.BackButton;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.ConfirmOrderViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddAddressFragment extends Fragment {
    private BackButton backButton;
    private AutoCompleteTextView autoCompleteCity;
    private AutoCompleteTextView autoCompleteDistrict;
    private TextInputEditText detailAddress;
    private MaterialButton btnComplete;
    private ConfirmOrderViewModel confirmOrderViewModel;
    private SwitchCompat switchSetDefault;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        confirmOrderViewModel = new ViewModelProvider(requireActivity()).get(ConfirmOrderViewModel.class);
        initViews(view);
        observeViewModel();
    }

    private void initViews(View view) {
        backButton = view.findViewById(R.id.back_button);
        autoCompleteCity = view.findViewById(R.id.autoCompleteCity);
        autoCompleteDistrict = view.findViewById(R.id.autoCompleteDistrict);
        detailAddress = view.findViewById(R.id.detailAddress);
        btnComplete = view.findViewById(R.id.btnComplete);

        if (backButton != null) {
            backButton.setOnClickListener(v -> handleBackPressed());
        }

        String[] cities = {"Bình Dương", "TP. Hồ Chí Minh", "Hà Nội", "Đà Nẵng", "Cần Thơ"};
        String[] districts = {"Đông Hoà", "Quận 1", "Thanh Xuân", "Hải Châu", "Ninh Kiều"};
        autoCompleteCity.setText("");
        autoCompleteCity.setAdapter(new android.widget.ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, cities));
        autoCompleteDistrict.setText("");
        autoCompleteDistrict.setAdapter(new android.widget.ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, districts));
        detailAddress.setText("");
        switchSetDefault = view.findViewById(R.id.switchSetDefault);

        btnComplete.setOnClickListener(v -> {
            String city = autoCompleteCity.getText().toString().trim();
            String district = autoCompleteDistrict.getText().toString().trim();
            String addressLine = detailAddress.getText() != null ? detailAddress.getText().toString().trim() : "";
            boolean isDefault = switchSetDefault.isChecked();

            if (city.isEmpty() || district.isEmpty() || addressLine.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            confirmOrderViewModel.createAddress(addressLine, district, city, isDefault);
        });
    }

    private void observeViewModel() {
        confirmOrderViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            // Show/hide loading if needed
        });

        confirmOrderViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), "Failed to add address: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        confirmOrderViewModel.getAddressesLiveData().observe(getViewLifecycleOwner(), addresses -> {
            if (addresses != null && !addresses.isEmpty()) {
                NavHostFragment.findNavController(this).navigateUp();
            }
        });
    }

    private void handleBackPressed() {
        if (isAdded()) {
            NavHostFragment.findNavController(this).navigateUp();
        }
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
        super.onPause();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showBottomNavigation();
        }
    }
}