package com.pomme.foursquare.models.foursquare;

/**
 * Created by pomme on 2017-10-05.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("name")
    @Expose
    public String name;

}
