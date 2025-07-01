package com.example.coffeeshopmanagementandroid.ui.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.example.coffeeshopmanagementandroid.R;

public class OtpInput extends LinearLayout {
    private static final int OTP_LENGTH = 6;
    private final EditText[] otpFields = new EditText[OTP_LENGTH];

    public OtpInput(Context context) {
        super(context);
        init(context, null);
    }

    public OtpInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.otp_input, this, true);
        setOrientation(HORIZONTAL);

        otpFields[0] = findViewById(R.id.number_1);
        otpFields[1] = findViewById(R.id.number_2);
        otpFields[2] = findViewById(R.id.number_3);
        otpFields[3] = findViewById(R.id.number_4);
        otpFields[4] = findViewById(R.id.number_5);
        otpFields[5] = findViewById(R.id.number_6);

        setupOtpFields();
    }

    private void setupOtpFields() {
        for (int i = 0; i < OTP_LENGTH; i++) {
            final int index = i;
            otpFields[i].setInputType(InputType.TYPE_CLASS_NUMBER);
            otpFields[i].setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});

            otpFields[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1 && index < OTP_LENGTH - 1) {
                        otpFields[index + 1].requestFocus();
                    } else if (s.length() == 0 && index > 0) {
                        otpFields[index - 1].requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
    }

    public String getOtp() {
        StringBuilder otp = new StringBuilder();
        for (EditText editText : otpFields) {
            otp.append(editText.getText().toString());
        }
        return otp.toString();
    }
}
