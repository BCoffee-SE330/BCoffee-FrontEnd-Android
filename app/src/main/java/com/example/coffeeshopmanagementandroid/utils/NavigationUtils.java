package com.example.coffeeshopmanagementandroid.utils;

import android.os.Bundle;
import android.util.Log;

import androidx.navigation.NavController;
import androidx.navigation.NavOptions;

public class NavigationUtils {

    /**
     * Kiểm tra trạng thái NavController hiện tại,
     * nếu không ở đúng destination, sẽ popBackStack về destination đúng.
     *
     * @param navController NavController cần kiểm tra
     * @param correctDestinationId id của destination đúng cần ở lại
     * @param logTag Tag dùng để log
     */
    public static void checkAndFixNavState(NavController navController, int correctDestinationId, String logTag) {
        if (navController.getCurrentDestination() != null &&
                navController.getCurrentDestination().getId() != correctDestinationId) {
            Log.w(logTag, "Incorrect destination detected: " +
                    navController.getCurrentDestination().getId() + ". Attempting to fix.");
            navController.popBackStack(correctDestinationId, false);
        }
    }

    /**
     * Thực hiện điều hướng (navigate) an toàn với log, kiểm tra current destination
     *
     * @param navController NavController để điều hướng
     * @param currentDestinationId id destination hiện tại dự kiến để thực hiện navigate
     * @param actionId id action trong nav graph để navigate
     * @param destinationName Tên destination dùng để log
     * @param logTag Tag dùng để log
     */
    public static void safeNavigate(
            NavController navController,
            int currentDestinationId,
            int actionId,
            String destinationName,
            String logTag
    ) {
        if (navController != null && navController.getCurrentDestination() != null) {
            if (navController.getCurrentDestination().getId() == currentDestinationId) {
                try {
                    Log.d(logTag, "Attempting to navigate to " + destinationName);
                    NavOptions navOptions = new NavOptions.Builder()
                            .setLaunchSingleTop(true)
                            .setPopUpTo(currentDestinationId, false) // Giữ current destination trong back stack
                            .build();
                    navController.navigate(actionId, null, navOptions);
                    Log.d(logTag, "Navigation to " + destinationName + " successful");
                } catch (Exception e) {
                    Log.e(logTag, "Navigation failed: " + e.getMessage());
                }
            } else {
                Log.e(logTag, "Cannot navigate to " + destinationName + ", current destination is: " +
                        navController.getCurrentDestination().getId());
            }
        } else {
            Log.e(logTag, "NavController or current destination is null");
        }
    }

    public static void safeNavigate(
            NavController navController,
            int currentDestinationId,
            int actionId,
            String destinationName,
            String logTag,
            Bundle args
    ) {
        if (navController != null && navController.getCurrentDestination() != null) {
            if (navController.getCurrentDestination().getId() == currentDestinationId) {
                try {
                    Log.d(logTag, "Attempting to navigate to " + destinationName);
                    NavOptions navOptions = new NavOptions.Builder()
                            .setLaunchSingleTop(true)
                            .setPopUpTo(currentDestinationId, false)
                            .build();
                    if (args != null) {
                        navController.navigate(actionId, args, navOptions);
                    } else {
                        navController.navigate(actionId, null, navOptions);
                    }
                    Log.d(logTag, "Navigation to " + destinationName + " successful");
                } catch (Exception e) {
                    Log.e(logTag, "Navigation failed: " + e.getMessage());
                }
            } else {
                Log.e(logTag, "Cannot navigate to " + destinationName + ", current destination is: " +
                        navController.getCurrentDestination().getId());
            }
        } else {
            Log.e(logTag, "NavController or current destination is null");
        }
    }
}