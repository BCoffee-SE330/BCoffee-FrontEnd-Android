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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import com.example.coffeeshopmanagementandroid.data.dto.user.request.UpdateUserRequest;

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
    private MaterialButton editAvatarButton;
    private Calendar calendar;

    private String initialLastName, initialFirstName, initialGender, initialDateOfBirth, initialPhone, initialEmail;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private boolean hasAvatarChanged = false;

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
        editAvatarButton = view.findViewById(R.id.editAvatarButton);

        calendar = Calendar.getInstance();

        setupGenderPicker();

        btnUpdateInformation.setOnClickListener(v -> setUpdateButtonClickListener());
        customImageButton.setOnClickListener(v -> setUpBtnUpdateAvatar());
        editAvatarButton.setOnClickListener(v -> {
            if (currentMode == EditMode.EDIT) {
                showImageOptions();
            }
        });

        customImageButton.setOnClickListener(v -> {
            if (currentMode == EditMode.EDIT) {
                showImageOptions();
            }
        });

        fetchAndObserverUserLive();
        updateUIForMode(EditMode.DONE);

        fetchAndObserverUserLive();
        updateUIForMode(EditMode.DONE);
    }

    private void setupGenderPicker() {
        // Define fixed gender options
        final String[] allGenders = {"Nam", "Nữ", "Khác"};

        // Create custom adapter that filters out the selected gender
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                allGenders
        ) {
            @Override
            public int getCount() {
                return allGenders.length - (autoCompleteGender.getText().toString().isEmpty() ? 0 : 1);
            }

            @Override
            public String getItem(int position) {
                String currentSelection = autoCompleteGender.getText().toString();
                int realPosition = position;

                for (int i = 0; i <= position; i++) {
                    if (allGenders[i].equals(currentSelection)) {
                        realPosition++;
                    }
                }

                if (realPosition >= allGenders.length) {
                    return allGenders[position];
                }

                return allGenders[realPosition];
            }
        };

        autoCompleteGender.setAdapter(adapter);
        autoCompleteGender.setThreshold(0);
        autoCompleteGender.setOnClickListener(v -> {
            ((ArrayAdapter<?>) autoCompleteGender.getAdapter()).notifyDataSetChanged();
            autoCompleteGender.showDropDown();
        });
        autoCompleteGender.setKeyListener(null);

        autoCompleteGender.setOnItemClickListener((parent, view, position, id) -> {
            adapter.notifyDataSetChanged();
        });
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
        editAvatarButton.setOnClickListener(v -> {
            if (currentMode == EditMode.EDIT) {
                showImageOptions();
            }
        });
        customImageButton.setOnClickListener(v -> {
            if (currentMode == EditMode.EDIT) {
                showImageOptions();
            }
        });
    }


    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            loadUserAvatar(selectedImageUri.toString());
            hasAvatarChanged = true;
        }
    }

    private void confirmDeleteAvatar() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Xóa ảnh đại diện")
                .setMessage("Bạn có chắc muốn xóa ảnh đại diện?")
                .setPositiveButton("Xác nhận", (dialog, which) -> {
                    userViewModel.deleteAvatar();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void updateUserInformation() {
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String genderText = autoCompleteGender.getText().toString().trim();
        String phone = editTextPhoneNumber.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String dob = editTextDateOfBirth.getText().toString().trim();

        if (!isValidEmail(email)) {
            Toast.makeText(requireContext(), "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create UpdateUserRequest object
        UpdateUserRequest updateRequest = new UpdateUserRequest();
        updateRequest.setName(firstName);
        updateRequest.setLastName(lastName);
        updateRequest.setEmail(email);
        updateRequest.setPhoneNumber(phone);
        updateRequest.setBirthDate(dob);
        updateRequest.setGender(genderText);

        // Update user information
        userViewModel.updateUserInfo(updateRequest);

        // If avatar was changed, upload the new image
        if (hasAvatarChanged && selectedImageUri != null) {
            uploadSelectedImage();
        }

        currentMode = EditMode.DONE;
        updateUIForMode(currentMode);
    }

    private void uploadSelectedImage() {
        try {
            File file = getFileFromUri(selectedImageUri);
            userViewModel.uploadAvatar(file);
        } catch (IOException e) {
            Toast.makeText(requireContext(), "Error preparing image: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private File getFileFromUri(Uri uri) throws IOException {
        String fileName = getFileName(uri);
        File file = new File(requireContext().getCacheDir(), fileName);
        InputStream input = requireContext().getContentResolver().openInputStream(uri);
        FileOutputStream output = new FileOutputStream(file);

        byte[] buffer = new byte[4 * 1024];
        int read;
        while ((read = input.read(buffer)) != -1) {
            output.write(buffer, 0, read);
        }

        output.flush();
        if (input != null) input.close();
        output.close();

        return file;
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = requireContext().getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index >= 0) {
                        result = cursor.getString(index);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


    private void showImageOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Ảnh đại diện");
        String[] options = {"Chọn ảnh từ thư mục", "Xóa ảnh đại diện"};

        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                openGallery();
            } else {
                confirmDeleteAvatar();
            }
        });

        builder.show();
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
            }
        });

        userViewModel.getAvatarResponseLiveData().observe(getViewLifecycleOwner(), urlMap -> {
            if (urlMap != null && urlMap.containsKey("url")) {
                String avatarUrl = urlMap.get("url");
                loadUserAvatar(avatarUrl);
                hasAvatarChanged = false;
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