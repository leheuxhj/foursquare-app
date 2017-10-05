package com.pomme.foursquare.ui.foodlist;

import com.pomme.foursquare.data.DataManager;
import com.pomme.foursquare.data.DataResult;
import com.pomme.foursquare.errorreport.ErrorReporting;
import com.pomme.foursquare.ui.UIEvent;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

/**
 * Created by pomme on 2017-10-03.
 */

public class FoodListPresenter implements FoodListContract.Presenter {

    private WeakReference<FoodListContract.View> view;
    private DataManager dataManager;
    private FoodListUIModel initialState = FoodListUIModel.inProgress();

    public FoodListPresenter(DataManager dataManager){
        this.dataManager = dataManager;
    }

    @Override
    public void setView(FoodListContract.View view) {
        this.view = new WeakReference<>(view);
    }

    // ---- UI EVENTS -> ACTIONS ----

    @Override
    public void uiEvent(UIEvent uiEvent) {
        view.get().subscribeToUIModel();
        UIEvent.UIEventType uiEventType = uiEvent.uiEventType;
        if (uiEventType == UIEvent.UIEventType.NEW_LIST_REQUEST) fetchFoodList(uiEvent);
        else if (uiEventType == UIEvent.UIEventType.OPEN_VENUE_INFO) fetchVenueInfo(uiEvent);
    }

    private void fetchFoodList(UIEvent uiEvent){
        dataManager.fetchFoodList();
    }

    private void fetchVenueInfo(UIEvent uiEvent){
        if (uiEvent.foodVenue != null) view.get().openDetailActivity(uiEvent.foodVenue);
    }

    // ---- DATA RESULT -> UIMODEL ----

    //Provide uiModels to the view as data is provided from Foursquare (or other service)
    @Override
    public Observable<FoodListUIModel> getUiModelObservable(){
        return resultObservable()
                .compose(uiModelObservable());
    }

    // provides data from Foursquare (or other service)
    private Observable<DataResult> resultObservable(){
        return dataManager.dataResultsObservable();
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

    // ---- ERROR HANDLING ----

    @Override
    public void onError(Throwable error) {
        ErrorReporting.sendErrorMessageToFabric(
                ErrorReporting.ErrorType.THROWABLE, String.valueOf(error.getMessage()));
    }

}
