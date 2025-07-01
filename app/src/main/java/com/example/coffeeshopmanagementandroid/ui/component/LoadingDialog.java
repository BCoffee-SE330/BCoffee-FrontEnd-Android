package com.example.coffeeshopmanagementandroid.ui.component;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.example.coffeeshopmanagementandroid.R;

public class LoadingDialog extends Dialog {

    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_loading);
        setCancelable(false);

        // Làm trong suốt nền dialog
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // Thêm hiệu ứng làm mờ màn hình xung quanh
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.dimAmount = 0.5f; // Giá trị từ 0.0f (không mờ) đến 1.0f (mờ hoàn toàn)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setAttributes(params);
        }
    }
}