package com.pomme.foursquare.data;

import android.content.Context;

import com.pomme.foursquare.R;
import com.pomme.foursquare.models.FoodVenue;
import com.pomme.foursquare.models.foursquare.FoursquareSearchResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pomme on 2017-10-03.
 */

public class DataManagerImpl implements DataManager {

    private FoursquareEndPoint foursquareEndPoint;
    private String clientId;
    private String clientSecret;
    private static final String latlong = "45.533970,-73.620745"; // Koolicar co-ordinates
    private static final String whichApi = "foursquare"; // options are foursquare or swarm
    private static final String apiVersion = "20171003";
    private static final String limit = "10";
    private static final String categoryId = "4d4b7105d754a06374d81259"; // category Id for "food"
    private static final String searchIntent = "checkin"; // set intent to checkin to ensure closest venues are returned

    public DataManagerImpl(Context context, FoursquareEndPoint foursquareEndPoint) {
        this.foursquareEndPoint = foursquareEndPoint;
        clientId = context.getString(R.string.foursquare_client_id);
        clientSecret = context.getString(R.string.foursquare_client_secret);
    }

    @Override
    public void fetchFoodList() {
        fetchFoodSearchFromFoursquare();
    }

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

    public void fetchFoodSearchFromFoursquare() {
        foursquareEndPoint
                .fetchFoodSearchResultsForLocation(latlong, clientId, clientSecret, whichApi,
                        apiVersion, limit, categoryId, searchIntent)
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
        // todo : convert to DataResult and send to views presenter
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
