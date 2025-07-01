package com.example.coffeeshopmanagementandroid.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.coffeeshopmanagementandroid.ui.fragment.main.FavoritesFragment;
import com.example.coffeeshopmanagementandroid.ui.fragment.main.HomeFragment;
import com.example.coffeeshopmanagementandroid.ui.fragment.main.OrdersFragment;
import com.example.coffeeshopmanagementandroid.ui.fragment.main.OtherFragment;

public class TabAdapter extends FragmentStateAdapter {
    public TabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new OrdersFragment();
            case 2:
                return new FavoritesFragment();
            case 3:
                return new OtherFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
