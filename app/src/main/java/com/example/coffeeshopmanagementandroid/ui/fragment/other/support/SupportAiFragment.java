package com.example.coffeeshopmanagementandroid.ui.fragment.other.support;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.content.Intent;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.di.repository.FloatingBubbleService;
import com.example.coffeeshopmanagementandroid.ui.MainActivity;
import com.example.coffeeshopmanagementandroid.ui.activity.ChatActivity;

public class SupportAiFragment extends Fragment {

    private ImageButton backButton;
    private Button btnActivateAI;
    private NavController navController;
    private ActivityResultLauncher<Intent> chatLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_support_ai, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);

        backButton = view.findViewById(R.id.backButton);
        btnActivateAI = view.findViewById(R.id.btnActivateAI);

        backButton.setOnClickListener(v -> navController.popBackStack());

        btnActivateAI.setOnClickListener(v -> {
            try {
                // Request permission if needed (Android 6.0+)
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M &&
                        !android.provider.Settings.canDrawOverlays(requireContext())) {

                    Intent intent = new Intent(
                            android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                    startActivity(intent);
                    Toast.makeText(requireContext(),
                            "Vui lòng cấp quyền hiển thị trên các ứng dụng khác",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    // Start the floating bubble service
                    Intent bubbleIntent = new Intent(requireContext(),
                            FloatingBubbleService.class);
                    requireActivity().startService(bubbleIntent);

                    // Navigate back to previous screen
                    navController.popBackStack();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(requireContext(),
                            "Không thể khởi động dịch vụ nổi: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(requireContext(),
                        "Không thể khởi động trợ lý ảo: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // We don't need chatLauncher anymore since we're using a service
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