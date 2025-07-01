package com.example.coffeeshopmanagementandroid.domain.model.product;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductModel implements Parcelable {
    private String productId;
    private String productName;
    private String productThumb;
    private String productDescription;
    private BigDecimal productPrice;
    private String productSlug;
    private BigDecimal productRatingsAverage;
    private Boolean productIsPublished;
    private Boolean productIsDeleted;
    private String productCategoryId;
    private boolean isFavorite;

    // Default constructor
    public ProductModel() {
    }

    // Constructor đầy đủ các trường cần thiết
    public ProductModel(String productId,
                        String productName,
                        String productDescription,
                        BigDecimal productPrice,
                        String productThumb,
                        BigDecimal productRatingsAverage,
                        String productCategoryId,
                        String productSlug,
                        Boolean productIsPublished,
                        Boolean productIsDeleted,
                        boolean isFavorite) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productThumb = productThumb;
        this.productRatingsAverage = productRatingsAverage;
        this.productCategoryId = productCategoryId;
        this.productSlug = productSlug;
        this.productIsPublished = productIsPublished;
        this.productIsDeleted = productIsDeleted;
        this.isFavorite = isFavorite;
    }

    protected ProductModel(Parcel in) {
        productId = in.readString();
        productName = in.readString();
        productThumb = in.readString();
        productDescription = in.readString();
        productSlug = in.readString();
        productPrice = (BigDecimal) in.readSerializable();
        productRatingsAverage = (BigDecimal) in.readSerializable();
        productIsPublished = (Boolean) in.readValue(Boolean.class.getClassLoader());
        productIsDeleted = (Boolean) in.readValue(Boolean.class.getClassLoader());
        productCategoryId = in.readString();
        isFavorite = in.readByte() != 0; // Read boolean value from Parcel
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getProductThumb() { return productThumb; }
    public void setProductThumb(String productThumb) { this.productThumb = productThumb; }
    public String getProductDescription() { return productDescription; }
    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }
    public BigDecimal getProductPrice() { return productPrice; }
    public void setProductPrice(BigDecimal productPrice) { this.productPrice = productPrice; }
    public BigDecimal getProductRatingsAverage() { return productRatingsAverage; }
    public void setProductRatingsAverage(BigDecimal productRatingsAverage) { this.productRatingsAverage = productRatingsAverage; }
    public String getProductSlug() { return productSlug; }
    public void setProductSlug(String productSlug) { this.productSlug = productSlug; }
    public Boolean getProductIsPublished() { return productIsPublished; }
    public void setProductIsPublished(Boolean productIsPublished) { this.productIsPublished = productIsPublished; }
    public Boolean getProductIsDeleted() { return productIsDeleted; }
    public void setProductIsDeleted(Boolean productIsDeleted) { this.productIsDeleted = productIsDeleted; }
    public String getProductCategoryId() { return productCategoryId; }
    public void setProductCategoryId(String productCategoryId) { this.productCategoryId = productCategoryId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductModel)) return false;
        ProductModel that = (ProductModel) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(productName, that.productName) &&
                Objects.equals(productThumb, that.productThumb) &&
                Objects.equals(productDescription, that.productDescription) &&
                Objects.equals(productPrice, that.productPrice) &&
                Objects.equals(productRatingsAverage, that.productRatingsAverage) &&
                Objects.equals(productSlug, that.productSlug) &&
                Objects.equals(productIsPublished, that.productIsPublished) &&
                Objects.equals(productIsDeleted, that.productIsDeleted) &&
                Objects.equals(productCategoryId, that.productCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, productThumb, productDescription, productPrice, productRatingsAverage, productSlug, productIsPublished, productIsDeleted, productCategoryId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeString(productId);
        parcel.writeString(productName);
        parcel.writeString(productThumb);
        parcel.writeString(productDescription);
        parcel.writeString(productSlug);
        parcel.writeSerializable(productPrice);
        parcel.writeSerializable(productRatingsAverage);
        parcel.writeValue(productIsPublished);
        parcel.writeValue(productIsDeleted);
        parcel.writeString(productCategoryId);
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}