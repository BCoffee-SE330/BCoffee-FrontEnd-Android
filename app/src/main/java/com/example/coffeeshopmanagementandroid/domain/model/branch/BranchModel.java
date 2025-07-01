package com.example.coffeeshopmanagementandroid.domain.model.branch;

import java.io.Serializable;

public class BranchModel implements Serializable {
    private String id;
    private String createdAt;
    private String updatedAt;
    private String branchName;
    private String branchAddress;
    private String branchPhone;
    private String branchEmail;
    private String managerId;
    private String managerName;

    public BranchModel() {}

    public BranchModel(String id, String createdAt, String updatedAt, String branchName, String branchAddress,
                       String branchPhone, String branchEmail, String managerId, String managerName) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.branchName = branchName;
        this.branchAddress = branchAddress;
        this.branchPhone = branchPhone;
        this.branchEmail = branchEmail;
        this.managerId = managerId;
        this.managerName = managerName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public String getBranchPhone() {
        return branchPhone;
    }

    public void setBranchPhone(String branchPhone) {
        this.branchPhone = branchPhone;
    }

    public String getBranchEmail() {
        return branchEmail;
    }

    public void setBranchEmail(String branchEmail) {
        this.branchEmail = branchEmail;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
}