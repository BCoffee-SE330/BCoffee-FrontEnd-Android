package com.example.coffeeshopmanagementandroid.ui.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.example.coffeeshopmanagementandroid.R;

public class AuthInput extends LinearLayout {
    private TextView inputLabel;
    private EditText inputField;
    private Drawable eyeIcon, eyeOffIcon;
    private boolean isPasswordField = false;
    private boolean isPasswordVisible = false;

    public AuthInput(Context context) {
        super(context);
        init(context, null);
    }

    public AuthInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.auth_input, this, true);
        setOrientation(VERTICAL);

        inputLabel = findViewById(R.id.label_text);
        inputField = findViewById(R.id.input_field);

        eyeIcon = ContextCompat.getDrawable(context, R.drawable.eye_icon);
        eyeOffIcon = ContextCompat.getDrawable(context, R.drawable.eye_close_icon);

        int inputType = InputType.TYPE_CLASS_TEXT;

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AuthInput);
            String labelText = typedArray.getString(R.styleable.AuthInput_labelText);
            String hintText = typedArray.getString(R.styleable.AuthInput_hintText);
            inputType = typedArray.getInt(R.styleable.AuthInput_inputType, InputType.TYPE_CLASS_TEXT);

            inputLabel.setText(labelText != null ? labelText : "Label");
            inputField.setHint(hintText != null ? hintText : "Label");

            // Gán input type
            inputField.setInputType(inputType);

            // Kiểm tra loại input
            isPasswordField = (inputType) == 129;


            Log.d("INPUT TYPE", "Raw inputType: " + (inputType | InputType.TYPE_TEXT_VARIATION_PASSWORD));
            Log.d("(inputType & InputType.TYPE_TEXT_VARIATION_PASSWORD)", String.valueOf(typedArray.getInt(R.styleable.AuthInput_inputType, InputType.TYPE_CLASS_TEXT)));
            Log.d("INPUT TYPE", "isPasswordField: " + isPasswordField);

            if (isPasswordField) {
                setPasswordToggleDrawable();
                inputField.setOnTouchListener((v, event) -> {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        int drawableEnd = inputField.getRight() - inputField.getCompoundDrawables()[2].getBounds().width();
                        if (event.getRawX() >= drawableEnd) {
                            togglePasswordVisibility();
                            return true;
                        }
                    }
                    return false;
                });
            }

            typedArray.recycle();
        }
    }

    private void setPasswordToggleDrawable() {
        Drawable drawable = isPasswordVisible ? eyeIcon : eyeOffIcon;
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
        inputField.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
    }

    private void togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible;

        // Lưu font hiện tại
        Typeface currentTypeface = inputField.getTypeface();

        // Thay đổi input type
        inputField.setInputType(isPasswordVisible ?
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));

        // Đặt lại font chữ để tránh thay đổi
        inputField.setTypeface(currentTypeface);

        inputField.setSelection(inputField.getText().length()); // Giữ vị trí con trỏ
        setPasswordToggleDrawable();
    }


    public void setLabel(String label) {
        inputLabel.setText(label);
    }

    public String getLabel() {
        return inputLabel.getText().toString();
    }

    public void setText(String text) {
        inputField.setText(text);
    }

    public String getText() {
        return inputField.getText().toString();
    }

    public void setHint(String hint) {
        inputField.setHint(hint);
    }

    public EditText getEditText() {
        return inputField;
    }
}