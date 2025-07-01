package com.example.coffeeshopmanagementandroid.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.coffeeshopmanagementandroid.data.dto.discount.response.LiteProductResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DiscountModel implements Parcelable {
    private String discountId;
    private String discountName;
    private String discountDescription;
    private String discountType;
    private BigDecimal discountValue;
    private String discountCode;
    private LocalDateTime discountStartDate;
    private LocalDateTime discountEndDate;
    private int discountMaxUses;
    private int discountUserCount;
    private int discountMaxPerUser;
    private BigDecimal discountMinOrderValue;
    private Boolean discountIsActive;
    private String discountBranchId;
    private String discountBranchName;
    private List<LiteProductResponse> products;

    public DiscountModel() {}

    protected DiscountModel(Parcel in) {
        discountId = in.readString();
        discountName = in.readString();
        discountDescription = in.readString();
        discountType = in.readString();
        discountValue = in.readByte() == 0 ? null : new BigDecimal(in.readString());
        discountCode = in.readString();
        String startDateStr = in.readString();
        discountStartDate = startDateStr == null ? null : LocalDateTime.parse(startDateStr);
        String endDateStr = in.readString();
        discountEndDate = endDateStr == null ? null : LocalDateTime.parse(endDateStr);
        discountMaxUses = in.readInt();
        discountUserCount = in.readInt();
        discountMaxPerUser = in.readInt();
        discountMinOrderValue = in.readByte() == 0 ? null : new BigDecimal(in.readString());
        byte tmpDiscountIsActive = in.readByte();
        discountIsActive = tmpDiscountIsActive == 0 ? null : tmpDiscountIsActive == 1;
        discountBranchId = in.readString();
        discountBranchName = in.readString();
        products = new ArrayList<>();
        in.readTypedList(products, LiteProductResponse.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(discountId);
        dest.writeString(discountName);
        dest.writeString(discountDescription);
        dest.writeString(discountType);
        if (discountValue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(discountValue.toString());
        }
        dest.writeString(discountCode);
        dest.writeString(discountStartDate != null ? discountStartDate.toString() : null);
        dest.writeString(discountEndDate != null ? discountEndDate.toString() : null);
        dest.writeInt(discountMaxUses);
        dest.writeInt(discountUserCount);
        dest.writeInt(discountMaxPerUser);
        if (discountMinOrderValue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(discountMinOrderValue.toString());
        }
        if (discountIsActive == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) (discountIsActive ? 1 : 2));
        }
        dest.writeString(discountBranchId);
        dest.writeString(discountBranchName);
        dest.writeTypedList(products);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DiscountModel> CREATOR = new Creator<DiscountModel>() {
        @Override
        public DiscountModel createFromParcel(Parcel in) {
            return new DiscountModel(in);
        }

        @Override
        public DiscountModel[] newArray(int size) {
            return new DiscountModel[size];
        }
    };

    // Getters and setters

    public String getDiscountId() { return discountId; }
    public void setDiscountId(String discountId) { this.discountId = discountId; }

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

    public int getDiscountMaxUses() { return discountMaxUses; }
    public void setDiscountMaxUses(int discountMaxUses) { this.discountMaxUses = discountMaxUses; }

    public int getDiscountUserCount() { return discountUserCount; }
    public void setDiscountUserCount(int discountUserCount) { this.discountUserCount = discountUserCount; }

    public int getDiscountMaxPerUser() { return discountMaxPerUser; }
    public void setDiscountMaxPerUser(int discountMaxPerUser) { this.discountMaxPerUser = discountMaxPerUser; }

    public BigDecimal getDiscountMinOrderValue() { return discountMinOrderValue; }
    public void setDiscountMinOrderValue(BigDecimal discountMinOrderValue) { this.discountMinOrderValue = discountMinOrderValue; }

    public Boolean getDiscountIsActive() { return discountIsActive; }
    public void setDiscountIsActive(Boolean discountIsActive) { this.discountIsActive = discountIsActive; }

    public String getDiscountBranchId() { return discountBranchId; }
    public void setDiscountBranchId(String discountBranchId) { this.discountBranchId = discountBranchId; }

    public String getDiscountBranchName() { return discountBranchName; }
    public void setDiscountBranchName(String discountBranchName) { this.discountBranchName = discountBranchName; }

    public List<LiteProductResponse> getProducts() { return products; }
    public void setProducts(List<LiteProductResponse> products) { this.products = products; }
}