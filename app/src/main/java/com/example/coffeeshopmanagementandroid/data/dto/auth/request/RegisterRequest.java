package com.example.coffeeshopmanagementandroid.data.dto.auth.request;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("email")
    private final String email;
    @SerializedName("password")
    private final String password;
    @SerializedName("passwordConfirm")
    private final String passwordConfirm;
    @SerializedName("name")
    private final String name;
    @SerializedName("lastName")
    private final String lastName;
    @SerializedName("phoneNumber")
    private final String phoneNumber;
    @SerializedName("gender")
    private final String gender;
    @SerializedName("birthDate")
    private final String birthday;

    public RegisterRequest(String email, String password, String passwordConfirm, String name, String lastName, String phoneNumber, String gender, String birthday) {
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }
}
