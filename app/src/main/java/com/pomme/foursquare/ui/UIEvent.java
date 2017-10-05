package com.pomme.foursquare.ui;

import com.pomme.foursquare.models.FoodVenue;

/**
 * Created by pomme on 2017-10-04.
 */

public class UIEvent {

    public enum UIEventType {
        NEW_LIST_REQUEST,
        OPEN_VENUE_INFO
    }

    public final UIEventType uiEventType;
    public final FoodVenue foodVenue;

    private UIEvent(UIEventType uiEventType, FoodVenue foodVenue){
        this.uiEventType = uiEventType;
        this.foodVenue = foodVenue;
    }

    public static UIEvent newListRequest(){
        return new UIEvent(UIEventType.NEW_LIST_REQUEST, null);
    }

    public static UIEvent openVenueInfo(FoodVenue foodVenue){
        return new UIEvent(UIEventType.OPEN_VENUE_INFO, foodVenue);
    }

    // Suppress default constructor for noninstantiability
    private UIEvent() {
        throw new AssertionError();
    }

}
