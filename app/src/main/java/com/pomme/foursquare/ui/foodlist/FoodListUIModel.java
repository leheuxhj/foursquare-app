package com.pomme.foursquare.ui.foodlist;

import com.pomme.foursquare.models.FoodVenue;

import java.util.List;

/**
 * Created by pomme on 2017-10-03.
 */

public class FoodListUIModel {

    public final boolean inProgress;
    public final boolean success;
    public final String errorMessage;
    public final List<FoodVenue> foodVenues;

    private FoodListUIModel(boolean inProgress, boolean success, String errorMessage,
                            List<FoodVenue> foodVenues){
        this.inProgress = inProgress;
        this.success = success;
        this.errorMessage = errorMessage;
        this.foodVenues = foodVenues;
    }

    public static FoodListUIModel inProgress(){
        return new FoodListUIModel(true, false, null, null);
    }

    public static FoodListUIModel success(List<FoodVenue> foodVenues){
        return new FoodListUIModel(false, true, null, foodVenues);
    }

    public static FoodListUIModel failure(String errorMessage){
        return new FoodListUIModel(false, false, errorMessage, null);
    }

    public static FoodListUIModel idle(){
        return new FoodListUIModel(false, false, null, null);
    }

    // Suppress default constructor for noninstantiability
    private FoodListUIModel() {
        throw new AssertionError();
    }

}
