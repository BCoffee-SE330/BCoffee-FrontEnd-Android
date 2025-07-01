package com.example.coffeeshopmanagementandroid.ui.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import android.widget.TextView;

import com.example.coffeeshopmanagementandroid.R;

public class BackButton extends LinearLayout {
    private Toolbar toolbar;
    private TextView backButtonText;

    public BackButton(Context context) {
        super(context);
        init(context);
    }

    public BackButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BackButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.back_button, this, true);
        toolbar = findViewById(R.id.toolbar);
        backButtonText = findViewById(R.id.back_button_text);
    }

    /**
     * Gán click listener cho nút back trong Toolbar.
     */
    public void setOnBackClickListener(OnClickListener listener) {
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(listener);
        }
    }

    /**
     * Cho phép set lại text hiển thị bên cạnh nút back (nếu có).
     */
    public void setText(String text) {
        if (backButtonText != null) {
            backButtonText.setText(text);
        }
    }
}
