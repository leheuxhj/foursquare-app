package com.pomme.foursquare;

/**
 * Created by pomme on 2017-10-04.
 */

import com.pomme.foursquare.data.DataManager;
import com.pomme.foursquare.data.DataResult;
import com.pomme.foursquare.models.FoodVenue;
import com.pomme.foursquare.ui.foodlist.FoodListPresenter;
import com.pomme.foursquare.ui.foodlist.FoodListUIModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.TestObserver;
import io.reactivex.subjects.PublishSubject;

import static org.mockito.Mockito.when;

/**
 * Created by pomme on 2017-10-03.
 */

public class FoodListPresenterTest {

    @Mock
    DataManager dataManager;

    private FoodListPresenter foodListPresenter;

    private final static String errorMsg = "Failed to get info";
    private DataResult failResult;
    private DataResult successResult;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        failResult = DataResult.failure(errorMsg);

        FoodVenue venue1 = new FoodVenue("venue1", "soup", "3 J st");
        FoodVenue venue2 = new FoodVenue("venue2", "fries", "4 K st");
        List<FoodVenue> venues = new ArrayList<>();
        venues.add(venue1);
        venues.add(venue2);
        successResult = DataResult.success(venues);

        foodListPresenter = new FoodListPresenter(dataManager);
    }

    @Test
    public void errorReturnedFromVenueSearch(){
        // given:
        TestObserver<FoodListUIModel> observer = new TestObserver<>();
        PublishSubject<DataResult> dataResultPublishSubject = PublishSubject.create();
        when(dataManager.dataResultsObservable()).thenReturn(dataResultPublishSubject);
        List<FoodListUIModel> modelList = new ArrayList<>();

        // when:
        foodListPresenter.getUiModelObservable().subscribe(observer);
        dataResultPublishSubject.onNext(failResult);
        dataResultPublishSubject.onComplete();

        // then:
        observer.assertNoErrors();
        //Check first value is FoodListUIModel.inProgress
        observer.assertValueAt(0, value ->
                value.inProgress && !value.success && value.errorMessage == null && value.foodVenues == null);
        //Check second value is FoodListUIModel.failure
        observer.assertValueAt(1, value ->
                !value.inProgress && !value.success && !value.errorMessage.isEmpty() && value.foodVenues == null);
    }

    @Test
    public void resultsReturnedFromSuccessfulVenueSearch() {
        // given:
        TestObserver<FoodListUIModel> observer = new TestObserver<>();
        PublishSubject<DataResult> dataResultPublishSubject = PublishSubject.create();
        when(dataManager.dataResultsObservable()).thenReturn(dataResultPublishSubject);

        // when:
        foodListPresenter.getUiModelObservable().subscribe(observer);
        dataResultPublishSubject.onNext(successResult);
        dataResultPublishSubject.onComplete();

        // then:
        observer.assertNoErrors();
        //Check first value is FoodListUIModel.inProgress
        observer.assertValueAt(0, value ->
                value.inProgress && !value.success && value.errorMessage == null && value.foodVenues == null);
        //Check second value is FoodListUIModel.success
        observer.assertValueAt(1, value ->
                !value.inProgress && value.success && value.errorMessage == null && !value.foodVenues.isEmpty());
    }

}

