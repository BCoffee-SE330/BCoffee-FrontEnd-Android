package com.example.coffeeshopmanagementandroid.data.dto.discount.response;

import android.os.Parcel;
import android.os.Parcelable;

public class LiteProductResponse implements Parcelable {
    private String id;
    private String name;
    private String thumb;
    private String price;
    private String ratingsAverage;

    public LiteProductResponse() {}

    protected LiteProductResponse(Parcel in) {
        id = in.readString();
        name = in.readString();
        thumb = in.readString();
        price = in.readString();
        ratingsAverage = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(thumb);
        dest.writeString(price);
        dest.writeString(ratingsAverage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<LiteProductResponse> CREATOR = new Creator<LiteProductResponse>() {
        @Override
        public LiteProductResponse createFromParcel(Parcel in) {
            return new LiteProductResponse(in);
        }

        @Override
        public LiteProductResponse[] newArray(int size) {
            return new LiteProductResponse[size];
        }
    };

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getThumb() { return thumb; }
    public void setThumb(String thumb) { this.thumb = thumb; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price;}
    public String getRatingAverage() {
        return ratingsAverage;
    }

    public void setRatingAverage(String ratingsAverage) {
        this.ratingsAverage = ratingsAverage;
    }
}
