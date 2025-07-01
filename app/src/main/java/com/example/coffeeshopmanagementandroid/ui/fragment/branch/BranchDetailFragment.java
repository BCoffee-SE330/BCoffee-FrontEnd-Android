package com.example.coffeeshopmanagementandroid.ui.fragment.branch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.branch.BranchModel;
import com.example.coffeeshopmanagementandroid.ui.MainActivity;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.BranchViewModel;

import android.os.Build;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BranchDetailFragment extends Fragment {
    private BranchViewModel branchViewModel;
    private NavController navController;
    private ImageButton backButton;

    // UI elements
    private TextView tvBranchName;
    private TextView tvBranchAddress;
    private TextView tvBranchPhone;
    private TextView tvBranchEmail;
    private TextView tvManagerName;
    private TextView tvCreatedAt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.branch_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel
        branchViewModel = new ViewModelProvider(this).get(BranchViewModel.class);
        navController = NavHostFragment.findNavController(this);

        // Initialize UI elements
        backButton = view.findViewById(R.id.backButtonBranchDetail);
        tvBranchName = view.findViewById(R.id.tvBranchName);
        tvBranchAddress = view.findViewById(R.id.tvBranchAddress);
        tvBranchPhone = view.findViewById(R.id.tvBranchPhone);
        tvBranchEmail = view.findViewById(R.id.tvBranchEmail);
        tvManagerName = view.findViewById(R.id.tvManagerName);
        tvCreatedAt = view.findViewById(R.id.tvCreatedAt);

        // Set back button click listener
        backButton.setOnClickListener(v -> navController.popBackStack());

        // Get branch ID from arguments
        String branchId = null;
        if (getArguments() != null) {
            branchId = getArguments().getString("branchId");
        }

        // Fetch branch details if branchId is available
        if (branchId != null && !branchId.isEmpty()) {
            branchViewModel.fetchBranchById(branchId);
            observeBranchData();
        } else {
            Toast.makeText(requireContext(), "Branch ID not found", Toast.LENGTH_SHORT).show();
            navController.popBackStack();
        }
    }

    private void observeBranchData() {
        branchViewModel.getBranchModelLiveData().observe(getViewLifecycleOwner(), branch -> {
            if (branch != null) {
                displayBranchDetails(branch);
            } else {
                Toast.makeText(requireContext(), "Branch details not found", Toast.LENGTH_SHORT).show();
                navController.popBackStack();
            }
        });
    }

    private void displayBranchDetails(BranchModel branch) {
        tvBranchName.setText(branch.getBranchName());
        tvBranchAddress.setText(branch.getBranchAddress());
        tvBranchPhone.setText(branch.getBranchPhone());
        tvBranchEmail.setText(branch.getBranchEmail());
        tvManagerName.setText(branch.getManagerName());
        tvCreatedAt.setText(formatToVietnameseDate(branch.getCreatedAt()));
    }

    private String formatToVietnameseDate(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isEmpty()) {
            return "-";
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // For modern Android versions
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr);

                // Get day of week in Vietnamese
                String dayOfWeek;
                switch (dateTime.getDayOfWeek()) {
                    case MONDAY: dayOfWeek = "Thứ Hai"; break;
                    case TUESDAY: dayOfWeek = "Thứ Ba"; break;
                    case WEDNESDAY: dayOfWeek = "Thứ Tư"; break;
                    case THURSDAY: dayOfWeek = "Thứ Năm"; break;
                    case FRIDAY: dayOfWeek = "Thứ Sáu"; break;
                    case SATURDAY: dayOfWeek = "Thứ Bảy"; break;
                    case SUNDAY: dayOfWeek = "Chủ Nhật"; break;
                    default: dayOfWeek = ""; break;
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDate = dateTime.format(formatter);
                return dayOfWeek + ", ngày " + formattedDate;
            } else {
                // For older Android versions
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                Date date = inputFormat.parse(dateTimeStr);
                if (date != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);

                    String dayOfWeek;
                    switch (calendar.get(Calendar.DAY_OF_WEEK)) {
                        case Calendar.MONDAY: dayOfWeek = "Thứ Hai"; break;
                        case Calendar.TUESDAY: dayOfWeek = "Thứ Ba"; break;
                        case Calendar.WEDNESDAY: dayOfWeek = "Thứ Tư"; break;
                        case Calendar.THURSDAY: dayOfWeek = "Thứ Năm"; break;
                        case Calendar.FRIDAY: dayOfWeek = "Thứ Sáu"; break;
                        case Calendar.SATURDAY: dayOfWeek = "Thứ Bảy"; break;
                        case Calendar.SUNDAY: dayOfWeek = "Chủ Nhật"; break;
                        default: dayOfWeek = ""; break;
                    }

                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String formattedDate = outputFormat.format(date);
                    return dayOfWeek + ", ngày " + formattedDate;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateTimeStr;
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
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showBottomNavigation();
        }
        super.onPause();
    }
}