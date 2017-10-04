package com.pomme.foursquare.ui.foodlist;

import com.pomme.foursquare.data.DataManager;
import com.pomme.foursquare.data.DataResult;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

/**
 * Created by pomme on 2017-10-03.
 */

public class FoodListPresenter implements FoodListContract.Presenter {

    private DataManager dataManager;
    private FoodListUIModel initialState = FoodListUIModel.idle();

    public FoodListPresenter(DataManager dataManager){
        this.dataManager = dataManager;
    }

    @Override
    public void fetchFoodList() {
        dataManager.fetchFoodList();
    }

    //Provide uiModels to the view as data is provided from Foursquare (or other service)
    @Override
    public Observable<FoodListUIModel> getUiModelObservable(){
        return resultObservable()
                .compose(uiModelObservable());
    }

    // provides data from Foursquare (or other service)
    private Observable<DataResult> resultObservable(){
        return dataManager.dataResults();
    }

    // Take results from Foursquare (or other service) and get a uiModel for updating view
    private ObservableTransformer<DataResult, FoodListUIModel> uiModelObservable() {
        return results -> results.scan(initialState, (state, result) -> {
            if (result.success && result.foodVenues != null) {
                return FoodListUIModel.success(result.foodVenues);
            } else if (result.errorMessage != null) {
                return FoodListUIModel.failure(result.errorMessage);
            }
            throw new IllegalArgumentException();
        });
    }

}
