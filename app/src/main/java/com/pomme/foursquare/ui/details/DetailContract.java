package com.pomme.foursquare.ui.details;

import com.pomme.foursquare.models.FoodVenue;

/**
 * Created by pomme on 2017-10-05.
 */

public class DetailContract {

    interface Presenter{
        void setView(DetailContract.View view);
        void onError(FoodVenue venue);
    }

    interface View {
        void displayErrorMessage();
    }

}
