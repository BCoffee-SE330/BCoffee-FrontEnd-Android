package com.example.coffeeshopmanagementandroid.ui.fragment.auth;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.ui.activity.AuthActivity;
import com.example.coffeeshopmanagementandroid.ui.component.AuthButton;
import com.example.coffeeshopmanagementandroid.ui.component.AuthInput;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Chặn nút back, không thực hiện popBackStack
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        AuthButton createButton = view.findViewById(R.id.create_button);
        AuthInput emailInput = view.findViewById(R.id.email_input);
        AuthInput firstNameInput = view.findViewById(R.id.first_name_input);
        AuthInput lastNameInput = view.findViewById(R.id.last_name_input);
        AuthInput passwordInput = view.findViewById(R.id.password_input);
        AuthInput confirmPasswordInput = view.findViewById(R.id.confirm_password_input);

        createButton.setOnClickListener(v -> navigateToVerify());
        createButton.setButtonText("Create");

        emailInput.setLabel("Email");
        emailInput.setHint("Type your Email");

        firstNameInput.setLabel("First Name");
        firstNameInput.setHint("Type First Name");

        lastNameInput.setLabel("Last Name");
        lastNameInput.setHint("Type Last Name");

        passwordInput.setLabel("Password");
        passwordInput.setHint("Type Your Password");

        confirmPasswordInput.setLabel("Confirm Password");
        confirmPasswordInput.setHint("Type Your Password Again");
        return view;
    }
    private void navigateToVerify() {
        if (getActivity() == null) {
            Log.e("RegisterFragment", "❌ Activity is null!");
            return;
        }
        if (!(getActivity() instanceof AuthActivity)) {
            Log.e("RegisterFragment", "❌ Activity is not AuthActivity!");
            return;
        }

        Log.d("RegisterFragment", "✅ Navigating to VerifyFragment...");
        ((AuthActivity) getActivity()).switchFragment(new VerifyFragment());
    }
}