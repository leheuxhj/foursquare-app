package com.pomme.foursquare.data;

import io.reactivex.Observable;

/**
 * Created by pomme on 2017-10-03.
 */

public interface DataManager {

    Observable<DataResult> dataResults();

}
