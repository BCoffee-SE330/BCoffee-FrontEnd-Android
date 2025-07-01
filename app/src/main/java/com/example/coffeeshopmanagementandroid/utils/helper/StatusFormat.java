package com.example.coffeeshopmanagementandroid.utils.helper;

import android.content.Context;
import androidx.core.content.ContextCompat;

import com.example.coffeeshopmanagementandroid.R;

public class StatusFormat {
    public static int getColor(Context context, String status) {
        switch (status) {
            case "HOÀN TẤT":
            case "ĐÃ GIAO HÀNG":
                return ContextCompat.getColor(context, R.color.primary_400);
            case "ĐÃ HUỶ":
                return ContextCompat.getColor(context, R.color.error_500);
            case "ĐANG XỬ LÝ":
            case "ĐANG CHỜ":
            case "ĐANG GIAO HÀNG":
                return ContextCompat.getColor(context, R.color.warning_500);
            default:
                return ContextCompat.getColor(context, R.color.warning_500); // fallback
        }
    }
}
