package com.example.coffeeshopmanagementandroid.ui.fragment.notification;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.notification.NotificationModel;
import com.example.coffeeshopmanagementandroid.ui.adapter.NotificationAdapter;
import com.example.coffeeshopmanagementandroid.ui.fragment.other.BaseOtherFragment;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.NotificationViewModel;
import com.example.coffeeshopmanagementandroid.utils.NavigationUtils;
import com.example.coffeeshopmanagementandroid.utils.SpaceItemDecoration;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.OrderSortBy;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotificationFragment extends BaseOtherFragment {

    private RecyclerView notificationRecyclerView;
    private NotificationAdapter notificationAdapter;
    private NavController navController;
    private NotificationViewModel notificationViewModel;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_notification;
    }

    @Override
    protected void initViews(View view) {
        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        setUpRecyclerView(view);
        navController = NavHostFragment.findNavController(this);
        observeNotifications();
        notificationViewModel.fetchNotifications(1, 15, SortType.DESC, OrderSortBy.CREATED_AT);
    }

    private void setUpRecyclerView(View view) {
        notificationRecyclerView = view.findViewById(R.id.notificationRecyclerView);
        notificationAdapter = new NotificationAdapter(new ArrayList<>(), notification -> {
            notificationViewModel.markNotificationAsRead(notification.getId());
            Bundle args = new Bundle();
            args.putString("notificationId", notification.getId());
            NavigationUtils.safeNavigate(
                    navController,
                    R.id.notificationFragment,
                    R.id.action_notificationFragment_to_detailNotificationFragment,
                    "DetailNotificationFragment",
                    "NotificationFragment",
                    args
            );
        });
        notificationRecyclerView.setAdapter(notificationAdapter);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        int marginTop = getResources().getDimensionPixelOffset(R.dimen.vertical_spacing);
        notificationRecyclerView.addItemDecoration(new SpaceItemDecoration().setTop(marginTop));
    }

    private void observeNotifications() {
        notificationViewModel.getNotificationModelsLiveData().observe(getViewLifecycleOwner(), notificationModels -> {
            if (notificationModels != null) {
                notificationAdapter.updateNotifications(notificationModels);
            } else {
                notificationAdapter.updateNotifications(new ArrayList<>());
            }
        });
        notificationViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}