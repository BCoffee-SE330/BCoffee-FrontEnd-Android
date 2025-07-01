// AuthButton.java
package com.example.coffeeshopmanagementandroid.ui.component; // Replace with your actual package name

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.coffeeshopmanagementandroid.R; // Replace with your R file

public class SocialButton extends LinearLayout {

    private ImageView buttonIcon;
    private TextView buttonText;

    public SocialButton(Context context) {
        super(context);
        init(context, null);
    }

    public SocialButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.social_button, this, true); // Use your layout name

        buttonIcon = findViewById(R.id.buttonIcon);
        buttonText = findViewById(R.id.buttonText);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AuthButton);

            String text = typedArray.getString(R.styleable.AuthButton_authButtonText);
            Drawable icon = typedArray.getDrawable(R.styleable.AuthButton_authButtonIcon);

            if (text != null) {
                buttonText.setText(text);
            }

            if (icon != null) {
                buttonIcon.setImageDrawable(icon);
            }

            typedArray.recycle();
        }
    }

    public void setButtonText(String text) {
        buttonText.setText(text);
    }

    public String getButtonText() {
        return buttonText.getText().toString();
    }

    public void setButtonIcon(Drawable icon) {
        buttonIcon.setImageDrawable(icon);
    }

    public void setButtonIcon(int resId) {
        buttonIcon.setImageResource(resId);
    }

    public ImageView getButtonIconView() {
        return buttonIcon;
    }

    public TextView getButtonTextView() {
        return buttonText;
    }
}