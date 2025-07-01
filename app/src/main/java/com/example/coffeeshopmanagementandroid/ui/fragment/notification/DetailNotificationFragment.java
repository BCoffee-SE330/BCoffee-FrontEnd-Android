package com.example.coffeeshopmanagementandroid.ui.fragment.notification;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.notification.NotificationModel;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.NotificationViewModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailNotificationFragment extends Fragment {

    private NotificationViewModel notificationViewModel;
    private NavController navController;

    private ImageButton backButton;
    private ImageView ivNotificationIcon;
    private TextView tvNotificationType, tvNotificationContent, tvSenderName, tvCreatedAt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        navController = NavHostFragment.findNavController(this);

        // Init views
        backButton = view.findViewById(R.id.backButtonDetailNotification);
        ivNotificationIcon = view.findViewById(R.id.ivNotificationIcon);
        tvNotificationType = view.findViewById(R.id.tvNotificationType);
        tvNotificationContent = view.findViewById(R.id.tvNotificationContent);
        tvCreatedAt = view.findViewById(R.id.tvCreatedAt);

        backButton.setOnClickListener(v -> navController.popBackStack());

        // Get notificationId from arguments
        String notificationId = null;
        if (getArguments() != null) {
            notificationId = getArguments().getString("notificationId");
        }

        if (notificationId != null) {
            notificationViewModel.fetchNotificationById(notificationId);
        }

        notificationViewModel.getNotificationModelMutableLiveData().observe(getViewLifecycleOwner(), notification -> {
            if (notification != null) {
                bindNotificationData(notification);
            }
        });
    }

    private void bindNotificationData(NotificationModel notification) {
        tvNotificationType.setText(nonNull(notification.getNotificationType()));
        tvNotificationContent.setText(nonNull(notification.getNotificationContent()));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O && notification.getCreatedAt() != null) {
            tvCreatedAt.setText(formatCreatedAt(notification.getCreatedAt()));
        } else {
            tvCreatedAt.setText("-");
        }
    }

    private String formatCreatedAt(String createdAt) {
        if (createdAt == null || createdAt.isEmpty()) return "-";
        try {
            LocalDateTime dateTime = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dateTime = LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }
            DateTimeFormatter formatter = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter.ofPattern("HH:mm:ss ' - Ngày' dd 'tháng' MM 'năm' yyyy");
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return dateTime.format(formatter);
            }
        } catch (Exception e) {
            return "-";
        }
        return "-";
    }

    private String nonNull(String s) {
        return s != null && !s.isEmpty() ? s : "-";
    }
}