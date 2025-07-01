package com.example.coffeeshopmanagementandroid.ui.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.example.coffeeshopmanagementandroid.R;

public class OtherButton extends AppCompatButton {
    public OtherButton(@NonNull Context context) {
        super(context);
        init(null);
    }

    public OtherButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public OtherButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setBackgroundResource(R.drawable.bg_others_button);
        setTextAppearance(R.style.text_sm_regular);
        setPadding(50, 16, 32, 16);
        setAllCaps(false);
        setGravity(Gravity.CENTER_VERTICAL);
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.OthersButton);
            String buttonText = typedArray.getString(R.styleable.OthersButton_buttonText);
            int rightIconRes = typedArray.getResourceId(R.styleable.OthersButton_rightIcon, 0);
            if (buttonText != null) {
                setText(buttonText);
            }
            setCompoundDrawablesWithIntrinsicBounds(0, 0, rightIconRes, 0);
//            setCompoundDrawablePadding(16);
            typedArray.recycle();
        }
    }
}
