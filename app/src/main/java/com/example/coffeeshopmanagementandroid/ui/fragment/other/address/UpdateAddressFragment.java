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
import androidx.navigation.fragment.NavHostFragment;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.ui.MainActivity;
import com.example.coffeeshopmanagementandroid.ui.component.BackButton;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class UpdateAddressFragment extends Fragment {
    private BackButton backButton;
    private AutoCompleteTextView autoCompleteCity;
    private AutoCompleteTextView autoCompleteDistrict;
    private TextInputEditText detailAddress;
    private SwitchCompat switchSetDefault;
    private MaterialButton btnDeleteAddress;
    private MaterialButton btnComplete;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_address, container, false); // Sử dụng cùng layout với AddAddressFragment
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        loadDataFromBundle();
    }

    private void initViews(View view) {
        backButton = view.findViewById(R.id.back_button);
        autoCompleteCity = view.findViewById(R.id.autoCompleteCity);
        autoCompleteDistrict = view.findViewById(R.id.autoCompleteDistrict);
        detailAddress = view.findViewById(R.id.detailAddress);
        switchSetDefault = view.findViewById(R.id.switchSetDefault);
        btnDeleteAddress = view.findViewById(R.id.btnDeleteAddress);
        btnComplete = view.findViewById(R.id.btnComplete);


        if (backButton != null) {
            backButton.setOnClickListener(v -> handleBackPressed());
        }

        // Tùy chọn mẫu cho AutoCompleteTextView (có thể thay bằng dữ liệu thực từ API)
        String[] cities = {"Bình Dương", "TP. Hồ Chí Minh", "Hà Nội", "Đà Nẵng", "Cần Thơ"};
        String[] districts = {"Đông Hoà", "Quận 1", "Thanh Xuân", "Hải Châu", "Ninh Kiều"};
        autoCompleteCity.setAdapter(new android.widget.ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, cities));
        autoCompleteDistrict.setAdapter(new android.widget.ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, districts));

        btnDeleteAddress.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Delete address", Toast.LENGTH_SHORT).show();
        });

        btnComplete.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Complete", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadDataFromBundle() {
        Bundle args = getArguments();
        if (args != null) {
            String addressId = args.getString("addressId");
            String addressLine = args.getString("addressLine");
            String addressDistrict = args.getString("addressDistrict");
            String addressCity = args.getString("addressCity");
            boolean isDefault = args.getBoolean("isDefault", false);

            if (addressLine != null) detailAddress.setText(addressLine);
            if (addressDistrict != null) autoCompleteDistrict.setText(addressDistrict);
            if (addressCity != null) autoCompleteCity.setText(addressCity);
            if (switchSetDefault != null) switchSetDefault.setChecked(isDefault);

            Toast.makeText(getContext(), "Đang chỉnh sửa địa chỉ: " + addressLine, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Không có dữ liệu địa chỉ", Toast.LENGTH_SHORT).show();
        }
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