package com.example.coffeeshopmanagementandroid.ui.fragment.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.ui.activity.AuthActivity;
import com.example.coffeeshopmanagementandroid.ui.component.OtherButton;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.LogoutViewModel;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.UserViewModel;
import com.example.coffeeshopmanagementandroid.utils.LoadingManager;
import com.example.coffeeshopmanagementandroid.utils.NavigationUtils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OtherFragment extends Fragment {

    private OtherButton btnProfile;
    private OtherButton btnAddress;
    private OtherButton btnSetting;
    private OtherButton btnNotification;
    private Button btnLogout;
    private NavController navController;
    private UserViewModel userViewModel;

    private LogoutViewModel logoutViewModel;

    public OtherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_other, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logoutViewModel = new ViewModelProvider(this).get(LogoutViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.fetchInformationCustomer();
        observeUserViewModel(view);
        navController = NavHostFragment.findNavController(this);

        NavigationUtils.checkAndFixNavState(navController, R.id.otherFragment, "OtherFragment");
        btnLogout = view.findViewById(R.id.btnLogout);
        btnProfile = view.findViewById(R.id.cBtnProfileUser);
        btnAddress = view.findViewById(R.id.cBtnAddress);
        btnSetting = view.findViewById(R.id.cBtnSetting);
        btnNotification = view.findViewById(R.id.cBtnNotification);

        setupListeners();
        observeViewModel();
    }

    private void setupListeners() {
        btnProfile.setOnClickListener(v ->
                NavigationUtils.safeNavigate(navController,
                        R.id.otherFragment,
                        R.id.action_otherFragment_to_profileFragment,
                        "ProfileFragment",
                        "OtherFragment"));

        btnAddress.setOnClickListener(v ->
                NavigationUtils.safeNavigate(navController,
                        R.id.otherFragment,
                        R.id.action_otherFragment_to_addressFragment,
                        "AddressFragment",
                        "OtherFragment"));

        btnSetting.setOnClickListener(v ->
                NavigationUtils.safeNavigate(navController,
                        R.id.otherFragment,
                        R.id.action_otherFragment_to_settingFragment,
                        "SettingFragment",
                        "OtherFragment"));

        btnNotification.setOnClickListener(v ->
                NavigationUtils.safeNavigate(navController,
                        R.id.otherFragment,
                        R.id.action_otherFragment_to_notificationFragment,
                        "NotificationFragment",
                        "OtherFragment"));
        btnLogout.setOnClickListener(v -> handleLogout());
    }

    private void observeViewModel() {
        // Observe loading state to show/hide ProgressBar
        logoutViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                if (isLoading) {
                    LoadingManager.getInstance().showLoading(requireContext());
                } else {
                    LoadingManager.getInstance().hideLoading();
                }
            }
        });


        // Observe logout result
        logoutViewModel.getLogoutResult().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                // Logout successful
                SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                prefs.edit().remove("access_token").remove("refresh_token").putBoolean("is_logged_in", false).apply();

                Intent intent = new Intent(requireContext(), AuthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        // Observe errors
        logoutViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), "Logout failed: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void observeUserViewModel(View view) {
        TextView tvUsername = view.findViewById(R.id.tvUsername);
        TextView tvRank = view.findViewById(R.id.tvRank);

        userViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                String fullName = user.getFullName();
                tvUsername.setText(fullName);
                tvRank.setText("Khách hàng");
            }
        });

        userViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), "Failed to load user info: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleLogout() {
//        logoutViewModel.logout();
        SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        prefs.edit().remove("id").remove("role").remove("access_token").remove("refresh_token").putBoolean("is_logged_in", false).apply();

        Intent intent = new Intent(requireContext(), AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }

}
