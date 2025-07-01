package com.example.coffeeshopmanagementandroid.ui.fragment.other.profile;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.ui.fragment.other.BaseOtherFragment;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.UserViewModel;
import com.example.coffeeshopmanagementandroid.utils.NavigationUtils;
import com.example.coffeeshopmanagementandroid.utils.enums.EditMode;
import com.example.coffeeshopmanagementandroid.utils.enums.Gender;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class ProfileFragment extends BaseOtherFragment {
    private UserViewModel userViewModel;
    private NavController navController;
    private EditMode currentMode = EditMode.DONE;

    private LinearLayout addPictureLayout;
    private TextView textAddPicture;
    private ImageButton customImageButton;
    private TextInputEditText editTextLastName, editTextFirstName, editTextDateOfBirth, editTextPhoneNumber, editTextEmail;
    private AutoCompleteTextView autoCompleteGender;
    private TextInputLayout dateOfBirthInputLayout;
    private Button btnUpdateInformation;
    private Calendar calendar;

    private String initialLastName, initialFirstName, initialGender, initialDateOfBirth, initialPhone, initialEmail;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initViews(@NonNull View view) {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        navController = NavHostFragment.findNavController(this);
        NavigationUtils.checkAndFixNavState(navController, R.id.profileFragment, "ProfileFragment");

        addPictureLayout = view.findViewById(R.id.addPictureLayout);
        textAddPicture = view.findViewById(R.id.textAddPicture);
        customImageButton = view.findViewById(R.id.customImageButton);
        editTextLastName = view.findViewById(R.id.editTextLastName);
        editTextFirstName = view.findViewById(R.id.editTextFirstName);
        autoCompleteGender = view.findViewById(R.id.autoCompleteGender);
        dateOfBirthInputLayout = view.findViewById(R.id.dateOfBirthInputLayout);
        editTextDateOfBirth = view.findViewById(R.id.editTextDateOfBirth);
        editTextPhoneNumber = view.findViewById(R.id.phoneNumberEditText);
        editTextEmail = view.findViewById(R.id.emailEditText);
        btnUpdateInformation = view.findViewById(R.id.btnUpdateInformation);

        calendar = Calendar.getInstance();

        setupGenderPicker();

        btnUpdateInformation.setOnClickListener(v -> setUpdateButtonClickListener());
        customImageButton.setOnClickListener(v -> setUpBtnUpdateAvatar());

        fetchAndObserverUserLive();
        updateUIForMode(EditMode.DONE);
    }

    private void setupGenderPicker() {
        Gender[] genderEnums = Gender.values();
        String[] genders = new String[genderEnums.length];

        for (int i = 0; i < genderEnums.length; i++) {
            genders[i] = genderEnums[i].getGender();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                genders
        );

        autoCompleteGender.setAdapter(adapter);
        autoCompleteGender.setThreshold(0); // Hiển thị dropdown ngay khi click
        autoCompleteGender.setOnClickListener(v -> autoCompleteGender.showDropDown());
        autoCompleteGender.setKeyListener(null);
    }

    private void showDatePickerDialog() {
        String currentDob = editTextDateOfBirth.getText().toString().trim();
        if (!currentDob.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                calendar.setTime(Objects.requireNonNull(sdf.parse(currentDob)));
            } catch (ParseException e) {
                e.printStackTrace();
                calendar = Calendar.getInstance();
            }
        } else {
            calendar = Calendar.getInstance();
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view1, selectedYear, selectedMonth, selectedDay) -> {
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    editTextDateOfBirth.setText(sdf.format(calendar.getTime()));
                },
                year,
                month,
                day
        );

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void setUpBtnUpdateAvatar() {
        // Bỏ qua logic liên quan đến ảnh theo yêu cầu
    }

    private void setUpdateButtonClickListener() {
        if (currentMode == EditMode.DONE) {
            currentMode = EditMode.EDIT;
            updateUIForMode(currentMode);
        } else {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc chắn muốn lưu các thay đổi không?")
                    .setPositiveButton("Xác nhận", (dialog, which) -> updateUserInformation())
                    .setNegativeButton("Huỷ", (dialog, which) -> {
                        updateUIForMode(EditMode.DONE);
                        restoreInitialValues();
                    })
                    .show();
        }
    }

    private void updateUIForMode(EditMode mode) {
        boolean editable = (mode == EditMode.EDIT);
        btnUpdateInformation.setText((mode == EditMode.DONE) ? EditMode.EDIT.getMode() : EditMode.DONE.getMode());

        editTextFirstName.setEnabled(editable);
        editTextLastName.setEnabled(editable);
        autoCompleteGender.setEnabled(editable);
        editTextDateOfBirth.setEnabled(editable);
        editTextPhoneNumber.setEnabled(editable);
        editTextEmail.setEnabled(editable);
        dateOfBirthInputLayout.setEndIconVisible(editable);

        if (editable) {
            editTextDateOfBirth.setKeyListener(null); // Ngăn nhập tay
            editTextDateOfBirth.setOnClickListener(v -> showDatePickerDialog());
            dateOfBirthInputLayout.setEndIconOnClickListener(v -> showDatePickerDialog());
        } else {
            editTextDateOfBirth.setOnClickListener(null);
            dateOfBirthInputLayout.setEndIconOnClickListener(null);
        }

        customImageButton.setEnabled(editable);
    }

    private void fetchAndObserverUserLive() {
        userViewModel.fetchInformationCustomer();
        userViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                editTextFirstName.setText(user.getUserName());
                editTextLastName.setText(user.getUserLastName());
                autoCompleteGender.setText(user.getUserGender() != null ? user.getUserGender() : "");
                if (user.getUserBirthday() != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    editTextDateOfBirth.setText(sdf.format(user.getUserBirthday()));
                }
                editTextPhoneNumber.setText(user.getUserPhone());
                editTextEmail.setText(user.getUserEmail());

                loadUserAvatar(user.getUserAvatar());

                initialFirstName = user.getUserName();
                initialLastName = user.getUserLastName();
                initialGender = user.getUserGender();
                initialPhone = user.getUserPhone();
                initialEmail = user.getUserEmail();
                if (user.getUserBirthday() != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    initialDateOfBirth = sdf.format(user.getUserBirthday());
                }
            } else {
                Toast.makeText(requireContext(), "No user data available", Toast.LENGTH_SHORT).show();
            }
        });
        userViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {});
        userViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void restoreInitialValues() {
        editTextFirstName.setText(initialFirstName);
        editTextLastName.setText(initialLastName);
        autoCompleteGender.setText(initialGender);
        editTextPhoneNumber.setText(initialPhone);
        editTextEmail.setText(initialEmail);
        editTextDateOfBirth.setText(initialDateOfBirth);
    }

    private void updateUserInformation() {
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String genderText = autoCompleteGender.getText().toString().trim();
        String phone = editTextPhoneNumber.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String dob = editTextDateOfBirth.getText().toString().trim();

        // Kiểm tra email hợp lệ
        if (!isValidEmail(email)) {
            Toast.makeText(requireContext(), "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        Gender gender = null;
        for (Gender g : Gender.values()) {
            if (g.getGender().equals(genderText)) {
                gender = g;
                break;
            }
        }

        // userViewModel.updateUserInfo(firstName, lastName, gender != null ? gender.name() : null, phone, email, dob);
        currentMode = EditMode.DONE;
        updateUIForMode(currentMode);
    }

    private void loadUserAvatar(String avatarUrl) {
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            Glide.with(requireContext())
                    .load(avatarUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .into(customImageButton);

            textAddPicture.setVisibility(View.GONE);
            addPictureLayout.setGravity(Gravity.CENTER);

            customImageButton.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            ));
        } else {
            customImageButton.setImageResource(R.drawable.plus_icon);
            textAddPicture.setVisibility(View.VISIBLE);
            customImageButton.setLayoutParams(new LinearLayout.LayoutParams(
                    getResources().getDimensionPixelSize(R.dimen.image_button_width),
                    getResources().getDimensionPixelSize(R.dimen.image_button_height)
            ));
        }
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}