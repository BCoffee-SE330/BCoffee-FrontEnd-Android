package com.example.coffeeshopmanagementandroid.ui.fragment.auth;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.ui.component.AuthButton;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.RegisterViewModel;
import com.example.coffeeshopmanagementandroid.utils.LoadingManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterFragment extends Fragment {
    private RegisterViewModel registerViewModel;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private TextInputEditText confirmPasswordInput;
    private TextInputEditText firstNameInput;
    private TextInputEditText lastNameInput;
    private TextInputEditText phoneNumberInput; // Added phone number field
    private AutoCompleteTextView genderInput;
    private TextInputEditText birthdayInput;
    private AuthButton createButton;
    private TextInputLayout birthdayInputLayout;
    private Calendar calendar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Chặn nút back, không thực hiện popBackStack
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        initViews(view);

        // Setup UI
        setupUI();

        // Setup observers
        observeViewModel();

        // Setup click listeners
        setupClickListeners();
    }

    private void initViews(View view) {
        createButton = view.findViewById(R.id.create_button);
        emailInput = view.findViewById(R.id.email_input);
        firstNameInput = view.findViewById(R.id.first_name_input);
        lastNameInput = view.findViewById(R.id.last_name_input);
        phoneNumberInput = view.findViewById(R.id.phone_number_input); // Initialize phone number
        passwordInput = view.findViewById(R.id.password_input);
        confirmPasswordInput = view.findViewById(R.id.confirm_password_input);
        genderInput = view.findViewById(R.id.gender_input);
        birthdayInput = view.findViewById(R.id.birthday_input);
        birthdayInputLayout = view.findViewById(R.id.birthday_input_layout);
        calendar = Calendar.getInstance();
    }

    private void setupUI() {
        createButton.setButtonText("Create");

        // Setup gender dropdown
        String[] genders = {"Nam", "Nữ"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, genders);
        genderInput.setAdapter(genderAdapter);
        genderInput.setThreshold(0);
        genderInput.setOnClickListener(v -> genderInput.showDropDown());

        // Setup birthday picker
        birthdayInput.setKeyListener(null); // Ngăn nhập tay
        birthdayInput.setOnClickListener(v -> showDatePickerDialog());
        birthdayInputLayout.setEndIconOnClickListener(v -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view1, selectedYear, selectedMonth, selectedDay) -> {
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    birthdayInput.setText(sdf.format(calendar.getTime()));
                },
                year,
                month,
                day
        );

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void setupClickListeners() {
        createButton.setOnClickListener(v -> handleRegister());
    }

    private void observeViewModel() {
        registerViewModel.getIsLoading().observe(getViewLifecycleOwner(), this::handleLoadingState);
        registerViewModel.getRegisterSuccess().observe(getViewLifecycleOwner(), this::handleRegisterSuccess);
        registerViewModel.getSuccessMessage().observe(getViewLifecycleOwner(), this::handleSuccessMessage);
        registerViewModel.getErrorMessage().observe(getViewLifecycleOwner(), this::handleErrorMessage);
        registerViewModel.getValidationErrors().observe(getViewLifecycleOwner(), this::handleValidationErrors);
    }

    private void handleRegister() {
        clearErrors();

        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();
        String firstName = firstNameInput.getText().toString().trim();
        String lastName = lastNameInput.getText().toString().trim();
        String phoneNumber = phoneNumberInput.getText().toString().trim();
        // Normalize phone number
        phoneNumber = phoneNumber.replaceAll("[^0-9]", "");
        if (phoneNumber.startsWith("1") && phoneNumber.length() == 9) {
            phoneNumber = "0" + phoneNumber;
        }
        String gender = genderInput.getText().toString().trim();
        String birthday = birthdayInput.getText().toString().trim();

        // Validation checks
        if (email.isEmpty()) {
            emailInput.setError("Email không được trống");
            emailInput.requestFocus();
            return;
        }

        if (firstName.isEmpty()) {
            firstNameInput.setError("Tên không được trống");
            firstNameInput.requestFocus();
            return;
        }

        if (lastName.isEmpty()) {
            lastNameInput.setError("Họ không được trống");
            lastNameInput.requestFocus();
            return;
        }

        if (phoneNumber.isEmpty()) {
            phoneNumberInput.setError("Số điện thoại không được trống");
            phoneNumberInput.requestFocus();
            return;
        }

        if (!phoneNumber.matches("^0[0-9]{9}$")) {
            phoneNumberInput.setError("Số điện thoại phải bắt đầu bằng 0 và có đúng 10 chữ số");
            phoneNumberInput.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordInput.setError("Mật khẩu không được trống");
            passwordInput.requestFocus();
            return;
        }

        if (confirmPassword.isEmpty()) {
            confirmPasswordInput.setError("Xác nhận mật khẩu không được trống");
            confirmPasswordInput.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordInput.setError("Mật khẩu không trùng");
            confirmPasswordInput.requestFocus();
            return;
        }

        if (gender.isEmpty()) {
            genderInput.setError("Giới tính không được trống");
            genderInput.requestFocus();
            return;
        }

        if (birthday.isEmpty()) {
            birthdayInput.setError("Ngày sinh không được trống");
            birthdayInput.requestFocus();
            return;
        }

        if (!isValidBirthday(birthday)) {
            birthdayInput.setError("Định dạng ngày sinh không hợp lệ. Sử dụng dd/MM/yyyy và ngày phải trong quá khứ");
            birthdayInput.requestFocus();
            return;
        }

        // Convert birthday to ISO 8601 format after validation
        birthday = convertDdMmYyyyToIso(birthday);

        Log.d("RegisterFragment", "Phone number before sending: '" + phoneNumber + "'");
        Log.d("RegisterFragment", "Birthday before sending: '" + birthday + "'");

        createButton.setEnabled(false);
        registerViewModel.register(email, password, confirmPassword, firstName, lastName, phoneNumber, gender, birthday);
    }

    private String convertDdMmYyyyToIso(String dateStr) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            inputFormat.setLenient(false);
            Date date = inputFormat.parse(dateStr);

            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            isoFormat.setTimeZone(TimeZone.getTimeZone("UTC" + 7 * 60 * 60 * 1000));
            assert date != null;
            return isoFormat.format(date);
        } catch (ParseException e) {
            Log.e("RegisterFragment", "Failed to convert date to ISO: " + dateStr, e);
            return dateStr; // Fallback to original string in case of error
        }
    }

    private boolean isValidBirthday(String birthday) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(birthday);
            Date today = new Date();
            return date != null && !date.after(today);
        } catch (ParseException e) {
            Log.e("RegisterFragment", "Invalid birthday format: " + birthday, e);
            return false;
        }
    }

    private void handleLoadingState(Boolean isLoading) {
        if (isLoading == null) return;

        if (isLoading) {
            LoadingManager.getInstance().showLoading(requireContext());
        } else {
            LoadingManager.getInstance().hideLoading();
            createButton.setEnabled(true);
        }
    }

    private void handleRegisterSuccess(Boolean success) {
        if (success != null && success) {
            navigateToLogin();
        }
    }

    private void handleSuccessMessage(String message) {
        if (message != null && !message.isEmpty()) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private void handleErrorMessage(String error) {
        if (error != null && !error.isEmpty()) {
            Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show();
        }
    }

    private void handleValidationErrors(Map<String, String> errors) {
        if (errors == null) return;

        clearErrors();

        if (errors.containsKey("email")) {
            emailInput.setError(errors.get("email"));
        }
        if (errors.containsKey("firstName")) {
            firstNameInput.setError(errors.get("firstName"));
        }
        if (errors.containsKey("lastName")) {
            lastNameInput.setError(errors.get("lastName"));
        }
        if (errors.containsKey("phoneNumber")) {
            phoneNumberInput.setError(errors.get("phoneNumber"));
        }
        if (errors.containsKey("password")) {
            passwordInput.setError(errors.get("password"));
        }
        if (errors.containsKey("confirmPassword")) {
            confirmPasswordInput.setError(errors.get("confirmPassword"));
        }
        if (errors.containsKey("gender")) {
            genderInput.setError(errors.get("gender"));
        }
        if (errors.containsKey("birthday")) {
            birthdayInput.setError(errors.get("birthday"));
        }

        focusOnFirstError(errors);
    }

    private void clearErrors() {
        emailInput.setError(null);
        firstNameInput.setError(null);
        lastNameInput.setError(null);
        phoneNumberInput.setError(null); // Clear phone number error
        passwordInput.setError(null);
        confirmPasswordInput.setError(null);
        genderInput.setError(null);
        birthdayInput.setError(null);
    }

    private void focusOnFirstError(Map<String, String> errors) {
        if (errors.containsKey("email")) {
            emailInput.requestFocus();
        } else if (errors.containsKey("firstName")) {
            firstNameInput.requestFocus();
        } else if (errors.containsKey("lastName")) {
            lastNameInput.requestFocus();
        } else if (errors.containsKey("phoneNumber")) {
            phoneNumberInput.requestFocus();
        } else if (errors.containsKey("password")) {
            passwordInput.requestFocus();
        } else if (errors.containsKey("confirmPassword")) {
            confirmPasswordInput.requestFocus();
        } else if (errors.containsKey("gender")) {
            genderInput.requestFocus();
        } else if (errors.containsKey("birthday")) {
            birthdayInput.requestFocus();
        }
    }

    private void navigateToLogin() {
        try {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_registerFragment_to_loginFragment);
            Log.d("RegisterFragment", "✅ Navigated to LoginFragment");
        } catch (Exception e) {
            Log.e("RegisterFragment", "❌ Navigation failed: " + e.getMessage(), e);
            Toast.makeText(requireContext(), "Registration successful! Please login.", Toast.LENGTH_SHORT).show();
        }
    }
}