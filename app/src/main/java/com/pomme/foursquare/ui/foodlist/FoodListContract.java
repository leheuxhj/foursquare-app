package com.pomme.foursquare.ui.foodlist;

/**
 * Created by pomme on 2017-10-04.
 */

import com.pomme.foursquare.models.FoodVenue;
import com.pomme.foursquare.ui.UIEvent;

import io.reactivex.Observable;

/**
 * Created by pomme on 2017-10-03.
 */

public interface FoodListContract {

    interface Presenter {
        void setView(FoodListContract.View view);
        void uiEvent(UIEvent event);
        Observable<FoodListUIModel> getUiModelObservable();
        void onError(Throwable error);
    }

    interface View {
        void subscribeToUIModel();
        void openDetailActivity(FoodVenue venue);
    }
}
