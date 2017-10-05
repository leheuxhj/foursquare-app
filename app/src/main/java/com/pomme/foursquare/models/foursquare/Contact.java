package com.pomme.foursquare.models.foursquare;

/**
 * Created by pomme on 2017-10-04.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contact {

    @SerializedName("formattedPhone")
    @Expose
    public String formattedPhone;

}
