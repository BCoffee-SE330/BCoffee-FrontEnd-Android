package com.example.coffeeshopmanagementandroid.data.dto.discount.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class DiscountResponse {
    private String id;
    private String createdAt;
    private String updatedAt;
    private String discountName;
    private String discountDescription;
    private String discountType;
    private BigDecimal discountValue;
    private String discountCode;
    private LocalDateTime discountStartDate;
    private LocalDateTime discountEndDate;
    private int discountMaxUsers;
    private int discountUserCount;
    private int discountMaxPerUser;
    private BigDecimal discountMinOrderValue;
    private boolean discountIsActive;
    private String branchId;
    private String branchName;
    private List<LiteProductResponse> products;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public String getDiscountName() { return discountName; }
    public void setDiscountName(String discountName) { this.discountName = discountName; }

    public String getDiscountDescription() { return discountDescription; }
    public void setDiscountDescription(String discountDescription) { this.discountDescription = discountDescription; }

    public String getDiscountType() { return discountType; }
    public void setDiscountType(String discountType) { this.discountType = discountType; }

    public BigDecimal getDiscountValue() { return discountValue; }
    public void setDiscountValue(BigDecimal discountValue) { this.discountValue = discountValue; }

    public String getDiscountCode() { return discountCode; }
    public void setDiscountCode(String discountCode) { this.discountCode = discountCode; }

    public LocalDateTime getDiscountStartDate() { return discountStartDate; }
    public void setDiscountStartDate(LocalDateTime discountStartDate) { this.discountStartDate = discountStartDate; }

    public LocalDateTime getDiscountEndDate() { return discountEndDate; }
    public void setDiscountEndDate(LocalDateTime discountEndDate) { this.discountEndDate = discountEndDate; }

    public int getDiscountMaxUsers() { return discountMaxUsers; }
    public void setDiscountMaxUsers(int discountMaxUsers) { this.discountMaxUsers = discountMaxUsers; }

    public int getDiscountUserCount() { return discountUserCount; }
    public void setDiscountUserCount(int discountUserCount) { this.discountUserCount = discountUserCount; }

    public int getDiscountMaxPerUser() { return discountMaxPerUser; }
    public void setDiscountMaxPerUser(int discountMaxPerUser) { this.discountMaxPerUser = discountMaxPerUser; }

    public BigDecimal getDiscountMinOrderValue() { return discountMinOrderValue; }
    public void setDiscountMinOrderValue(BigDecimal discountMinOrderValue) { this.discountMinOrderValue = discountMinOrderValue; }

    public boolean isDiscountIsActive() { return discountIsActive; }
    public void setDiscountIsActive(boolean discountIsActive) { this.discountIsActive = discountIsActive; }

    public String getBranchId() { return branchId; }
    public void setBranchId(String branchId) { this.branchId = branchId; }

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }

    public List<LiteProductResponse> getProducts() { return products; }
    public void setProducts(List<LiteProductResponse> products) { this.products = products; }
}