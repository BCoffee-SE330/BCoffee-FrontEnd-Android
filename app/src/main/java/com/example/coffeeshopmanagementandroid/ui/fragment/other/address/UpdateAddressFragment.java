package com.example.coffeeshopmanagementandroid.ui.fragment.other.address;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
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
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.ui.MainActivity;
import com.example.coffeeshopmanagementandroid.ui.component.BackButton;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.AddressViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.UUID;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UpdateAddressFragment extends Fragment {
    private BackButton backButton;
    private NavController navController;
    private AutoCompleteTextView autoCompleteCity;
    private AutoCompleteTextView autoCompleteDistrict;
    private TextInputEditText detailAddress;
    private SwitchCompat switchSetDefault;
    private MaterialButton btnDeleteAddress;
    private MaterialButton btnComplete;
    private AddressViewModel addressViewModel;
    private UUID addressId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_address, container, false);
    }

    private void initViews(View view) {
        // Initialize other UI components (not the back button)
        autoCompleteCity = view.findViewById(R.id.autoCompleteCity);
        autoCompleteDistrict = view.findViewById(R.id.autoCompleteDistrict);
        detailAddress = view.findViewById(R.id.detailAddress);
        switchSetDefault = view.findViewById(R.id.switchSetDefault);
        btnDeleteAddress = view.findViewById(R.id.btnDeleteAddress);
        btnComplete = view.findViewById(R.id.btnComplete);

        // Set up other button listeners
        btnDeleteAddress.setOnClickListener(v -> confirmDeleteAddress());
        btnComplete.setOnClickListener(v -> {
            if (validateInputs()) {
                updateAddress();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addressViewModel = new ViewModelProvider(requireActivity()).get(AddressViewModel.class);

        navController = NavHostFragment.findNavController(this);

        // Initialize back button first, separately from other views
        initBackButton(view);

        // Then initialize the rest of the UI
        initViews(view);
        setupObservers();
        loadDataFromBundle();
        setupDropdowns();
    }

    private void initBackButton(View view) {
        backButton = view.findViewById(R.id.back_button);
        if (backButton != null) {
            backButton.setOnClickListener(v -> navController.popBackStack());
        } else {
            android.util.Log.e("UpdateAddressFragment", "Back button not found");
        }
    }

    private void setupDropdowns() {
        // Sample data
        String[] cities = {"Bình Dương", "TP. Hồ Chí Minh", "Hà Nội", "Đà Nẵng", "Cần Thơ"};
        String[] districts = {"Đông Hoà", "Quận 1", "Thanh Xuân", "Hải Châu", "Ninh Kiều"};

        // Create adapters
        android.widget.ArrayAdapter<String> cityAdapter = new android.widget.ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                cities
        );

        android.widget.ArrayAdapter<String> districtAdapter = new android.widget.ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                districts
        );

        // Important: Set input type to 'none' programmatically
        autoCompleteCity.setInputType(android.text.InputType.TYPE_NULL);
        autoCompleteDistrict.setInputType(android.text.InputType.TYPE_NULL);

        // Set adapters
        autoCompleteCity.setAdapter(cityAdapter);
        autoCompleteDistrict.setAdapter(districtAdapter);

        // Set threshold (0 shows all options)
        autoCompleteCity.setThreshold(0);
        autoCompleteDistrict.setThreshold(0);

        // Add touch listeners as a workaround
        autoCompleteCity.setOnTouchListener((v, event) -> {
            autoCompleteCity.showDropDown();
            return false;
        });

        autoCompleteDistrict.setOnTouchListener((v, event) -> {
            autoCompleteDistrict.showDropDown();
            return false;
        });
    }
    private void setupObservers() {
        addressViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadDataFromBundle() {
        Bundle args = getArguments();
        if (args != null) {
            String addressIdStr = args.getString("addressId");
            if (addressIdStr != null) {
                addressId = UUID.fromString(addressIdStr);
            }

            String addressLine = args.getString("addressLine");
            String addressDistrict = args.getString("addressDistrict");
            String addressCity = args.getString("addressCity");
            boolean isDefault = args.getBoolean("isDefault", false);

            if (addressLine != null) detailAddress.setText(addressLine);
            if (addressDistrict != null) autoCompleteDistrict.setText(addressDistrict);
            if (addressCity != null) autoCompleteCity.setText(addressCity);
            if (switchSetDefault != null) switchSetDefault.setChecked(isDefault);
        } else {
            Toast.makeText(getContext(), "Không có dữ liệu địa chỉ", Toast.LENGTH_SHORT).show();
            handleBackPressed();
        }
    }

    private boolean validateInputs() {
        boolean isValid = true;

        String city = autoCompleteCity.getText().toString().trim();
        String district = autoCompleteDistrict.getText().toString().trim();
        String line = detailAddress.getText().toString().trim();

        if (TextUtils.isEmpty(city)) {
            autoCompleteCity.setError("Vui lòng chọn thành phố");
            isValid = false;
        }

        if (TextUtils.isEmpty(district)) {
            autoCompleteDistrict.setError("Vui lòng chọn quận/huyện");
            isValid = false;
        }

        if (TextUtils.isEmpty(line)) {
            detailAddress.setError("Vui lòng nhập địa chỉ chi tiết");
            isValid = false;
        }

        return isValid;
    }

    private void updateAddress() {
        if (addressId == null) {
            Toast.makeText(getContext(), "Không tìm thấy ID địa chỉ", Toast.LENGTH_SHORT).show();
            return;
        }

        String addressLine = detailAddress.getText().toString().trim();
        String addressCity = autoCompleteCity.getText().toString().trim();
        String addressDistrict = autoCompleteDistrict.getText().toString().trim();
        boolean isDefault = switchSetDefault.isChecked();

        addressViewModel.updateAddress(
                addressId,
                addressLine,
                addressCity,
                addressDistrict,
                isDefault
        );

        // Navigate back after update
        handleBackPressed();
    }

    private void confirmDeleteAddress() {
        if (addressId == null) {
            Toast.makeText(getContext(), "Không tìm thấy ID địa chỉ", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(requireContext())
                .setTitle("Xóa địa chỉ")
                .setMessage("Bạn có chắc chắn muốn xóa địa chỉ này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    addressViewModel.deleteAddress(addressId);
                    handleBackPressed();
                })
                .setNegativeButton("Hủy", null)
                .show();
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