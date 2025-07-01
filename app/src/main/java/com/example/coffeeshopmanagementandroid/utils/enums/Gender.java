package com.example.coffeeshopmanagementandroid.utils.enums;

import androidx.annotation.NonNull;

public enum Gender {
    FEMALE("Nữ"),
    MALE("Nam"),
    OTHER("Khác");
    private final String gender;
    Gender(String gender) {
        this.gender = gender;
    }
    public String getGender() {
        return gender;
    }
    @NonNull
    @Override
    public String toString() {
        return gender;
    }
}
