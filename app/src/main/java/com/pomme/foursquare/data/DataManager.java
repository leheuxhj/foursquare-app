package com.pomme.foursquare.data;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by pomme on 2017-10-03.
 */

public interface DataManager {

    PublishSubject<DataResult> dataResultsObservable();
    void fetchFoodList();

}
