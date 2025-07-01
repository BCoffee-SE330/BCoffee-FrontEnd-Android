package com.example.coffeeshopmanagementandroid.ui.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.example.coffeeshopmanagementandroid.R;

public class AuthButton extends LinearLayout {
    private AppCompatButton authButton;

    public AuthButton(Context context) {
        super(context);
        init(context, null);
    }

    public AuthButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.auth_button, this, true); // Use your layout name

        authButton = findViewById(R.id.auth_button);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AuthButton);

            String text = typedArray.getString(R.styleable.AuthButton_authButtonText);


            if (text != null) {
                authButton.setText(text);
            }

            typedArray.recycle();
        }
    }

    public void setButtonText(String text) {
        authButton.setText(text);
    }

    public String getButtonText() {
        return authButton.getText().toString();
    }

    public void setOnClickListener(OnClickListener listener) {
        authButton.setOnClickListener(listener);
    }
}
