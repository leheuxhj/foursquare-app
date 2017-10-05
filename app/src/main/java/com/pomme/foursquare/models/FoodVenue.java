package com.pomme.foursquare.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pomme on 2017-10-03.
 */
public class FoodVenue implements Parcelable {

    public final String venueName;
    public final String address;
    public final String phoneNumber;

    public FoodVenue(String venueName, String address, String phoneNumber){
        this.venueName = venueName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    protected FoodVenue(Parcel in) {
        venueName = in.readString();
        address = in.readString();
        phoneNumber = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(venueName);
        dest.writeString(address);
        dest.writeString(phoneNumber);
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
