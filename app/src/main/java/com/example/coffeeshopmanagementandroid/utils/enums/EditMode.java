package com.example.coffeeshopmanagementandroid.utils.enums;

import androidx.annotation.NonNull;

public enum EditMode {
    EDIT("Sửa"),
    DONE("Hoàn thành");
    private final String mode;
    EditMode(String mode) {
        this.mode = mode;
    }
    public String getMode() {
        return mode;
    }
    @NonNull
    @Override
    public String toString() {
        return mode;
    }
}
