package com.example.coffeeshopmanagementandroid.ui.fragment.auth;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.ui.component.AuthButton;

public class VerifyFragment extends Fragment {

    private TextView textClock;
    private TextView resendButton;
    private AuthButton verifyButton;
    private CountDownTimer countDownTimer;
    private static final long TIMER_DURATION = 60000; // 60s
    private static final long INTERVAL = 1000; // 1s
    private boolean isCounting = false; // Kiểm tra timer có đang chạy không

    public VerifyFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verify, container, false);

        textClock = view.findViewById(R.id.textClock);
        resendButton = view.findViewById(R.id.resend_button);
        verifyButton = view.findViewById(R.id.verify_button);

        verifyButton.setButtonText("Verify");

        // Ban đầu không chạy countdown, hiển thị sẵn 0s
        textClock.setText("");
        resendButton.setEnabled(true); // Có thể ấn nút resend ngay từ đầu
        resendButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
        resendButton.setOnClickListener(v -> {
            if (!isCounting) { // Chỉ chạy nếu timer chưa chạy
                resendOTP();
                startCountdown();
            }
        });

        return view;
    }

    @SuppressLint("ResourceAsColor")
    private void startCountdown() {
        isCounting = true;
        resendButton.setEnabled(false); // Vô hiệu hóa nút resend
        resendButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondary_300));
        countDownTimer = new CountDownTimer(TIMER_DURATION, INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                textClock.setText("(" + millisUntilFinished / 1000 + "s)");
            }

            @Override
            public void onFinish() {
                textClock.setText("");
                resendButton.setEnabled(true); // Kích hoạt lại nút resend
                resendButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
                isCounting = false; // Đánh dấu timer đã kết thúc
            }
        }.start();
    }

    private void resendOTP() {
        // Code gửi lại mã OTP (có thể thêm API request nếu cần)
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
