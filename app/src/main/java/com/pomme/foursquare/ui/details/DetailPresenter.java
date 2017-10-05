package com.pomme.foursquare.ui.details;

import com.pomme.foursquare.errorreport.ErrorReporting;
import com.pomme.foursquare.models.FoodVenue;

import java.lang.ref.WeakReference;

/**
 * Created by pomme on 2017-10-05.
 */

public class DetailPresenter implements DetailContract.Presenter {

    private WeakReference<DetailContract.View> view;

    @Override
    public void setView(DetailContract.View view) {
        this.view = new WeakReference<>(view);
    }

    @Override
    public void onError(FoodVenue venue) {
        view.get().displayErrorMessage();
        String venueName = "venue unknown";
        if (venue != null && venue.venueName != null){
            venueName = venue.venueName;
        }
        ErrorReporting.sendErrorMessageToFabric(
                ErrorReporting.ErrorType.DETAIL_ERROR, String.valueOf(venueName));
    }

}
