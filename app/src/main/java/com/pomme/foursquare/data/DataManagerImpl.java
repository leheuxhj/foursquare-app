package com.pomme.foursquare.data;

import android.content.Context;

import com.pomme.foursquare.R;
import com.pomme.foursquare.models.FoodVenue;
import com.pomme.foursquare.models.foursquare.FoursquareSearchResponse;
import com.pomme.foursquare.models.foursquare.Venue;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pomme on 2017-10-03.
 */

public class DataManagerImpl implements DataManager {

    PublishSubject<DataResult> publishSubject = PublishSubject.create(); // todo send DataResult to presenter

    private FoursquareEndPoint foursquareEndPoint;
    private String clientId;
    private String clientSecret;
    private static final String latlong = "45.533714,-73.620218"; // Koolicar co-ordinates
    private static final String whichApi = "foursquare"; // options are foursquare or swarm
    private static final String apiVersion = "20171003";
    private static final String limit = "10";
    private static final String categoryId = "4d4b7105d754a06374d81259"; // category Id for "food"
    private static final String searchIntent = "browse"; // set intent to browse to get closest venues
    private static final String searchRadius = "200";


    public DataManagerImpl(Context context, FoursquareEndPoint foursquareEndPoint) {
        this.foursquareEndPoint = foursquareEndPoint;
        clientId = context.getString(R.string.foursquare_client_id);
        clientSecret = context.getString(R.string.foursquare_client_secret);
    }

    @Override
    public void fetchFoodList() {
        fetchFoodSearchFromFoursquare();
    }

    // Observers (such as in the FoodListPresenter) can get DataResults by subscribing via this method
    @Override
    public PublishSubject<DataResult> dataResultsObservable() {
        if (publishSubject.hasComplete()){
            publishSubject = PublishSubject.create();
        }
        return publishSubject;
    }

    private void fetchFoodSearchFromFoursquare() {
        foursquareEndPoint
                .fetchFoodSearchResultsForLocation(latlong, clientId, clientSecret, whichApi,
                        apiVersion, limit, categoryId, searchIntent, searchRadius)
                .enqueue(new Callback<FoursquareSearchResponse>() {
                    @Override
                    public void onResponse(Call<FoursquareSearchResponse> call, Response<FoursquareSearchResponse> response) {
                        try {
                            if(response.isSuccessful()) {
                                onSuccessfulSearchResponse(response.body());
                            } else {
                                onFoursquareError(response.raw().code());
                            }
                        } catch (Exception e) {
                            onException(e);
                        }
                    }
                    @Override
                    public void onFailure(Call<FoursquareSearchResponse> call, Throwable t) {
                        onNetworkFailure(t);
                    }
                });
    }

    private void onSuccessfulSearchResponse(FoursquareSearchResponse response){
        if (!publishSubject.hasComplete()){
            DataResult result = convertFoursquareSearchResponse(response);
            if (result != null) publishSubject.onNext(result);
        }
    }

    // Turning foursquare response into a format that the presenter can use (DataResult)
    private DataResult convertFoursquareSearchResponse(FoursquareSearchResponse response){
        if (response != null && response.response != null && response.response.venues != null) {
            List<Venue> venueList = response.response.venues;
            List<FoodVenue> foodVenueList = new ArrayList<>();
            for (Venue venue : venueList) {
                if (venue.name != null && !venue.name.isEmpty()) {
                    FoodVenue foodVenue = new FoodVenue(venue.name, null, null);
                    foodVenueList.add(foodVenue);
                }
            }
            return DataResult.success(foodVenueList);
        } else return null;
    }

    private void onFoursquareError(int errorCode){
        // todo : notify ui of error
    }

    private void onNetworkFailure(Throwable throwable){
        // todo : notify ui of error
    }

    private void onException(Exception e){
        // todo : notify ui of error
    }

}
