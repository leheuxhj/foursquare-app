package com.pomme.foursquare.models.foursquare;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pomme on 2017-10-04.
 */

public class Venue {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("contact")
    @Expose
    public Contact contact;
    @SerializedName("location")
    @Expose
    public Location location;

}
