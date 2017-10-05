package com.pomme.foursquare.data;

import android.content.Context;

import com.pomme.foursquare.R;
import com.pomme.foursquare.errorreport.ErrorReporting;
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

    private PublishSubject<DataResult> publishSubject = PublishSubject.create();

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
        clientId = context.getString(R.string.foursquareClientId);
        clientSecret = context.getString(R.string.foursquareClientSecret);
    }

    // ---- ACTIONS ----

    @Override
    public void fetchFoodList() {
        fetchFoodSearchFromFoursquare();
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

    // ---- DATA RESULTS ----

    // Observers (such as in the FoodListPresenter) can get DataResults by subscribing via this method
    @Override
    public PublishSubject<DataResult> dataResultsObservable() {
        if (publishSubject.hasComplete()){
            publishSubject = PublishSubject.create();
        }
        return publishSubject;
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
                FoodVenue foodVenue = convertFoursquareVenueToFoodVenue(venue);
                foodVenueList.add(foodVenue);
            }
            return DataResult.success(foodVenueList);
        } else return null;
    }

    private FoodVenue convertFoursquareVenueToFoodVenue(Venue venue){
        FoodVenue foodVenue = null;
        if (venue.name != null && !venue.name.isEmpty()) {
            // Name
            String venueName = venue.name;
            // Location
            String venueLocation = null;
            if (venue.location != null && venue.location.address != null
                    && !venue.location.address.isEmpty()) {
                venueLocation = venue.location.address;
            }
            // Category
            String venueCategory = null;
            if (venue.categories != null
                    && !venue.categories.isEmpty()
                    && venue.categories.get(0) != null
                    && venue.categories.get(0).name != null) {
                venueCategory = venue.categories.get(0).name;
            }
            foodVenue = new FoodVenue(venueName, venueLocation, venueCategory);
        }
        return foodVenue;
    }

    // ---- ERROR HANDLING ----

    private void onFoursquareError(int errorCode){
        ErrorReporting.sendErrorMessageToFabric(
                ErrorReporting.ErrorType.FOURSQUARE_ERROR, String.valueOf(errorCode));
        sendDataResultError(String.valueOf(errorCode));
    }

    private void onNetworkFailure(Throwable throwable){
        ErrorReporting.sendErrorMessageToFabric(
                ErrorReporting.ErrorType.NETWORK_FAILURE, throwable.getMessage());
        sendDataResultError(throwable.getMessage());
    }

    private void onException(Exception e){
        ErrorReporting.sendErrorMessageToFabric(
                ErrorReporting.ErrorType.EXCEPTION, e.getMessage());
        sendDataResultError(e.getMessage());
    }

    private void sendDataResultError(String errorMessage){
        if (!publishSubject.hasComplete()){
            DataResult result = DataResult.failure(errorMessage);
            publishSubject.onNext(result);
        }
    }

}
