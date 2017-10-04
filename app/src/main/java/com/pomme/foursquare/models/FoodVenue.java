package com.pomme.foursquare.models;

/**
 * Created by pomme on 2017-10-03.
 */

public class FoodVenue {

    final String venueName;

    public FoodVenue(String venueName){
        this.venueName = venueName;
    }

    // Suppress default constructor for non-instantiability
    private FoodVenue() {
        throw new AssertionError();
    }

}
