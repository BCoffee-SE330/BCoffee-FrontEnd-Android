package com.example.coffeeshopmanagementandroid.ui.fragment.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.auth.AuthModel;
import com.example.coffeeshopmanagementandroid.domain.model.auth.UserModel;
import com.example.coffeeshopmanagementandroid.ui.MainActivity;
import com.example.coffeeshopmanagementandroid.ui.activity.AuthActivity;
import com.example.coffeeshopmanagementandroid.ui.component.SocialButton;
import com.example.coffeeshopmanagementandroid.ui.component.AuthInput;
import com.example.coffeeshopmanagementandroid.ui.component.AuthButton;
import com.example.coffeeshopmanagementandroid.ui.fragment.main.HomeFragment;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.LoginViewModel;
import com.example.coffeeshopmanagementandroid.utils.LoadingManager;
import com.example.coffeeshopmanagementandroid.utils.SharedPreferencesUtils;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {
    private LoginViewModel loginViewModel;
    private AuthInput emailInput;
    private AuthInput passwordInput;
    private AuthButton loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Ánh xạ các thành phần UI
        emailInput = view.findViewById(R.id.email_input);
        passwordInput = view.findViewById(R.id.password_input);
        loginButton = view.findViewById(R.id.sign_in_button);
        SocialButton facebookButton = view.findViewById(R.id.facebook_button);
        SocialButton googleButton = view.findViewById(R.id.google_button);

        // Thiết lập giao diện
        emailInput.setLabel("Email");
        emailInput.setHint("Type your Email");

        passwordInput.setLabel("Password");
        passwordInput.setHint("Enter your password");

        facebookButton.setButtonIcon(R.drawable.facebook_icon);
        facebookButton.setButtonText("Facebook");

        googleButton.setButtonIcon(R.drawable.google_icon);
        googleButton.setButtonText("Google");

        // Xử lý sự kiện bấm đăng nhập
        loginButton.setOnClickListener(v -> handleLogin());

        // Xử lý sự kiện chuyển sang màn hình đăng ký
        view.findViewById(R.id.go_to_sign_up_button).setOnClickListener(v -> navigateToSignUp());

        // Lắng nghe kết quả đăng nhập từ ViewModel
        observeLoginResult();
    }

    private void handleLogin() {
        String email = emailInput.getText();
        String password = passwordInput.getText();
        CheckBox rememberMe = requireView().findViewById(R.id.checkBox);
        Boolean rememberMeValue = rememberMe.isChecked();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Email and password must not be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        loginButton.setEnabled(false);
        loginViewModel.login(email, password, rememberMeValue);
    }

    private void observeLoginResult() {
        // Observe loading state to show/hide ProgressBar
        loginViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> handleLoadingState(isLoading));

        // Observe user data and save to SharedPreferences
        loginViewModel.getUserLiveData().observe(getViewLifecycleOwner(), userModel -> handleUserData(userModel));

        // Observe authentication result and navigate
        loginViewModel.getAuthLiveData().observe(getViewLifecycleOwner(), authModel -> handleAuthResult(authModel));
    }

    private void handleLoadingState(Boolean isLoading) {
        if (isLoading == null) {
            return;
        }
        if (isLoading) {
            LoadingManager.getInstance().showLoading(requireContext());
        } else {
            LoadingManager.getInstance().hideLoading();
        }
    }

    private void handleUserData(UserModel userModel) {
        if (!isAdded()) return;

        if (userModel == null) return;
        SharedPreferencesUtils.saveUserPreferences(requireContext(), userModel.getId(), userModel.getRole().toString());
    }

    private void handleAuthResult(AuthModel authModel) {
        if (!isAdded()) return;

        if (authModel != null) {
            SharedPreferencesUtils.saveAuthPreferences(requireContext(), authModel.getAccessToken(), authModel.getRefreshToken(), true);
            navigateToMainActivity();
        } else {
            Toast.makeText(requireContext(), "Đăng nhập thất bại! Vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
        }

        loginButton.setEnabled(true);
    }

    private void navigateToMainActivity() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (isAdded() && getActivity() != null) {
                Intent intent = new Intent(requireContext(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        }, 500);
    }

    private void navigateToSignUp() {
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_loginFragment_to_registerFragment);
    }
}
