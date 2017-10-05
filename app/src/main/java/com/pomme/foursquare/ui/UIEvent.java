package com.pomme.foursquare.ui;

/**
 * Created by pomme on 2017-10-04.
 */

public class UIEvent {

    public enum UIEventType {
        NEW_LIST_REQUEST,
        OPEN_VENUE_INFO
    }

    public final UIEventType uiEventType;
    public final String venueId;

    private UIEvent(UIEventType uiEventType, String venueId){
        this.uiEventType = uiEventType;
        this.venueId = venueId;
    }

    public static UIEvent newListRequest(){
        return new UIEvent(UIEventType.NEW_LIST_REQUEST, null);
    }

    public static UIEvent openVenueInfo(String venueId){
        return new UIEvent(UIEventType.OPEN_VENUE_INFO, venueId);
    }

    // Suppress default constructor for noninstantiability
    private UIEvent() {
        throw new AssertionError();
    }

}
