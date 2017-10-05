package com.pomme.foursquare.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pomme on 2017-10-03.
 */
public class FoodVenue implements Parcelable {

    public final String venueName;
    public final String address;
    public final String category;

    public FoodVenue(String venueName, String address, String category){
        this.venueName = venueName;
        this.address = address;
        this.category = category;
    }

    protected FoodVenue(Parcel in) {
        venueName = in.readString();
        address = in.readString();
        category = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(venueName);
        dest.writeString(address);
        dest.writeString(category);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<FoodVenue> CREATOR = new Parcelable.Creator<FoodVenue>() {
        @Override
        public FoodVenue createFromParcel(Parcel in) {
            return new FoodVenue(in);
        }

        @Override
        public FoodVenue[] newArray(int size) {
            return new FoodVenue[size];
        }
    };

    // Suppress default constructor for non-instantiability
    private FoodVenue() {
        throw new AssertionError();
    }

}
