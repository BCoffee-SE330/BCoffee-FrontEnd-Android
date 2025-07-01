package com.example.coffeeshopmanagementandroid.domain.model.auth;

import com.example.coffeeshopmanagementandroid.utils.enums.Role;

public class UserModel {
    private String id;
    private Role role;

    public UserModel(String id, Role role) {
        this.id = id;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
