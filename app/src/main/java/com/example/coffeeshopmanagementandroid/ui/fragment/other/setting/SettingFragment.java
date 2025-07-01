package com.example.coffeeshopmanagementandroid.ui.fragment.other.setting;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.ui.fragment.other.BaseOtherFragment;
import com.example.coffeeshopmanagementandroid.utils.NavigationUtils;
import com.google.android.material.button.MaterialButton;

public class SettingFragment extends BaseOtherFragment {
    private MaterialButton btnChangePassword;
    private MaterialButton btnRemoveAccount;

    NavController navController;
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initViews(View view) {
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        btnRemoveAccount = view.findViewById(R.id.btnRemoveAccount);
        navController = NavHostFragment.findNavController(this);
        NavigationUtils.checkAndFixNavState(navController, R.id.settingFragment, "SettingFragment");
        setUpListener();
    }

    private void setUpListener() {
        btnChangePassword.setOnClickListener(v ->
                NavigationUtils.safeNavigate(navController,
                        R.id.settingFragment,
                        R.id.action_settingFragment_to_changePasswordFragment,
                        "ChangePasswordFragment",
                        "SettingFragment"));
        btnRemoveAccount.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Button Remove Account clicked", Toast.LENGTH_SHORT).show());
    }
}