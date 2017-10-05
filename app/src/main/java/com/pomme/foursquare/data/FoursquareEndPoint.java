package com.pomme.foursquare.data;

import com.pomme.foursquare.models.foursquare.FoursquareSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pomme on 2017-10-04.
 */

public interface FoursquareEndPoint {

    String CLIENT_ID = "client_id";
    String CLIENT_SECRET = "client_secret";
    String WHICH_API = "m"; // options are Foursquare or Swarm. Refer https://developer.foursquare.com/docs/api/configuration/versioning
    String API_VERSION = "v"; // version of API. Refer https://developer.foursquare.com/docs/api/configuration/versioning
    String LIMIT = "limit"; // number of venues to return
    String SECTION = "categoryId"; // type of venue to return https://developer.foursquare.com/docs/resources/categories
    String INTENT = "intent";
    String RADIUS = "radius";

    @GET("/v2/venues/search")
    Call<FoursquareSearchResponse> fetchFoodSearchResultsForLocation(@Query("ll") String latLong,
                                                                     @Query(CLIENT_ID) String clientId,
                                                                     @Query(CLIENT_SECRET) String clientSecret,
                                                                     @Query(WHICH_API) String foursquare,
                                                                     @Query(API_VERSION) String apiVersion,
                                                                     @Query(LIMIT) String limit,
                                                                     @Query(SECTION) String section,
                                                                     @Query(INTENT) String searchIntent,
                                                                     @Query(RADIUS) String searchRadius
    );

}
