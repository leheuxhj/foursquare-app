package com.pomme.foursquare.data;

import com.pomme.foursquare.models.FoodVenue;

import java.util.List;

/**
 * Created by pomme on 2017-10-04.
 */

public class DataResult {

    public final List<FoodVenue> foodVenues;
    public final boolean success;
    public final String errorMessage;

    private DataResult(boolean success, List<FoodVenue> foodVenues, String errorMessage){
        this.success = success;
        this.foodVenues = foodVenues;
        this.errorMessage = errorMessage;
    }

    public static DataResult success(List<FoodVenue> foodVenues){
        return new DataResult(true, foodVenues, null);
    }

    public static DataResult failure(String errorMessage){
        return new DataResult(false, null, errorMessage);
    }

    // Suppress default constructor for noninstantiability
    private DataResult() {
        throw new AssertionError();
    }

}
