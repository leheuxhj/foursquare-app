package com.pomme.foursquare.data;

import com.pomme.foursquare.models.FoodVenue;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by pomme on 2017-10-03.
 */

public class DataManagerImpl implements DataManager{

    // Currently returning fake result
    @Override
    public Observable<DataResult> dataResults() {
        FoodVenue venue1 = new FoodVenue("venue1");
        FoodVenue venue2 = new FoodVenue("venue2");
        FoodVenue venue3 = new FoodVenue("venue3");
        List<FoodVenue> venues = new ArrayList<>();
        venues.add(venue1);
        venues.add(venue2);
        venues.add(venue3);

        DataResult result = DataResult.success(venues);
        return Observable.just(result);
    }

}
