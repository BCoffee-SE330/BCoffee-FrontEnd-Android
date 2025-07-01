package com.example.coffeeshopmanagementandroid.ui.fragment.other;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.ui.MainActivity;
import com.example.coffeeshopmanagementandroid.ui.component.BackButton;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public abstract class BaseOtherFragment extends Fragment {
    protected BackButton backButton;
    protected NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);

        try {
            initViews(view);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        initBackButton(view);
    }

    private void initBackButton(View view) {
        backButton = view.findViewById(R.id.back_button);
        if (backButton != null) {
            backButton.setOnBackClickListener(v -> {
                handleBackPressed();
            });
        }
    }


    protected void handleBackPressed() {
        if (isAdded() && navController != null) {
            if (navController.getPreviousBackStackEntry() != null) {
                navController.popBackStack();
            } else {
                requireActivity().onBackPressed();
            }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        backButton = null;
        navController = null;
    }

    protected abstract int getLayoutResId();

    protected abstract void initViews(View view) throws Exception;
}