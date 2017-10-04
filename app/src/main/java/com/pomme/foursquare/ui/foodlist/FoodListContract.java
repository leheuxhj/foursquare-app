package com.pomme.foursquare.ui.foodlist;

/**
 * Created by pomme on 2017-10-04.
 */

import io.reactivex.Observable;

/**
 * Created by pomme on 2017-10-03.
 */

public interface FoodListContract {
    interface Presenter {
        void fetchFoodList();
        Observable<FoodListUIModel> getUiModelObservable();
    }
}
