package com.example.coffeeshopmanagementandroid.ui;

import static androidx.navigation.Navigation.findNavController;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.ui.activity.AuthActivity;
import com.example.coffeeshopmanagementandroid.ui.adapter.TabAdapter;
import com.example.coffeeshopmanagementandroid.ui.fragment.auth.LoginFragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint

public class MainActivity extends AppCompatActivity {
    private BottomAppBar bottomAppBar;
    private BottomNavigationView bottomNavigationView;
    private NavOptions navOptions;

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Kiểm tra trạng thái đăng nhập
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String accessToken = prefs.getString("access_token", null);
        String refreshToken = prefs.getString("refresh_token", null);
        boolean isLoggedIn = prefs.getBoolean("is_logged_in", false);

        if (!isUserLoggedIn()) {
            // Chuyển sang AuthActivity
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
            finish(); // Đóng MainActivity để không quay lại màn hình này
            return;
        }

        // Nếu đã đăng nhập, tiếp tục hiển thị MainActivity
        setContentView(R.layout.activity_main);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_main);
        if (navHostFragment != null) {
            NavOptions.Builder builder = new NavOptions.Builder();
            builder.setLaunchSingleTop(true);
            navOptions = builder.build();

            navController = navHostFragment.getNavController();
            navController.navigate(R.id.main_navigation);

        }
        bottomAppBar = findViewById(R.id.bottom_app_bar);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        bottomNavigationView.setSelectedItemId(R.id.homeFragment);
//        if (savedInstanceState == null) {
//            loadFragment(new LoginFragment());
//        }
    }

    // Để lại chưa làm

    private boolean isUserLoggedIn() {
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        // Kiểm tra trạng thái đăng nhập từ SharedPreferences

        return prefs.getBoolean("is_logged_in", false) && prefs.getString("access_token", null) != null;
    }

    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_main_container, fragment)
                .commit();
    }

    public void hideBottomNavigation() {
        if(bottomAppBar != null ){
            bottomAppBar.setVisibility(View.GONE);
        }
        if(bottomNavigationView != null) {
            bottomNavigationView.setVisibility(View.GONE);
        }
    }

    public void showBottomNavigation() {
        if(bottomAppBar != null ){
            bottomAppBar.setVisibility(View.VISIBLE);
        }
        if(bottomNavigationView != null) {
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_main);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            if (navController != null && navController.getCurrentDestination() != null) {
                if (navController.getCurrentDestination().getId() != R.id.homeFragment) {
                    navController.popBackStack(); // Quay lại fragment trước đó
                } else {
                    super.onBackPressed(); // Thoát ứng dụng nếu đang ở homeFragment
                }
            } else {
                super.onBackPressed(); // Gọi mặc định nếu không có NavController
            }
        } else {
            super.onBackPressed(); // Gọi mặc định nếu không tìm thấy NavHostFragment
        }
    }
}