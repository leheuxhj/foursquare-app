package com.pomme.foursquare.models.foursquare;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pomme on 2017-10-04.
 */

public class Location {

    @SerializedName("address")
    @Expose
    public String address;

}
