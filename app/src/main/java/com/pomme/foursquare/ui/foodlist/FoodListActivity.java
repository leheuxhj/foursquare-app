package com.pomme.foursquare.ui.foodlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.pomme.foursquare.R;
import com.pomme.foursquare.constants.FoursquareAppConstants;
import com.pomme.foursquare.models.FoodVenue;
import com.pomme.foursquare.ui.UIEvent;
import com.pomme.foursquare.ui.details.DetailActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FoodListActivity extends AppCompatActivity implements FoodListContract.View {

    @Inject
    FoodListPresenter presenter;

    @BindView(R.id.venueList)
    ListViewCompat venueListView;
    @BindView(R.id.venueListProgressBar)
    ProgressBar progressBar;

    private CompositeDisposable compositeDisposable;
    private Disposable uiModelDisposable;
    private boolean uiModelObserverHasOnComplete = false;

    private ArrayList<String> venueNames;
    private ArrayList<FoodVenue> venues;
    private final static String VENUE_LIST = "foodListActivity_venueList";
    private final static String VENUE_NAMES_LIST = "foodListActivity_venueNamesList";
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        presenter.setView(this);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // check if venues are stored in bundle, if not then fetch from Foursquare
        if (savedInstanceState != null && savedInstanceState.getParcelableArrayList(VENUE_LIST) != null) {
            venues = savedInstanceState.getParcelableArrayList(VENUE_LIST);
            venueNames = savedInstanceState.getStringArrayList(VENUE_NAMES_LIST);
            if (venues != null && venueNames != null && !venueNames.isEmpty())
                restoreStateOnConfigChange(venues, venueNames);
            else presenter.uiEvent(UIEvent.newListRequest());
        } else presenter.uiEvent(UIEvent.newListRequest());
    }

    private void restoreStateOnConfigChange(List<FoodVenue> venueList, List<String> venueNamesList){
        if (venueList != null && venueNamesList != null && !venueNamesList.isEmpty()){
            setUpListView();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (venues != null && venueNames != null){
            outState.putParcelableArrayList(VENUE_LIST, venues);
            outState.putStringArrayList(VENUE_NAMES_LIST, venueNames);
        }
    }

    @Override
    protected void onDestroy() {
        if (compositeDisposable != null) compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public void subscribeToUIModel(){
        if (compositeDisposable == null) compositeDisposable = new CompositeDisposable();

        if (uiModelDisposable == null || uiModelDisposable.isDisposed() || uiModelObserverHasOnComplete){
            uiModelObserverHasOnComplete = false;
            uiModelDisposable =
                    presenter.getUiModelObservable()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::updateUI,this::handleError,this::handleUiModelObserverOnComplete);
            compositeDisposable.add(uiModelDisposable);
        }
    }

    private void updateUI(FoodListUIModel uiModel){
        if (uiModel.success) updateUIWithNewList(uiModel);
        else if (uiModel.inProgress) updateUIForInProgress(uiModel);
        else updateUIForError(uiModel);
    }

    private void updateUIWithNewList(FoodListUIModel uiModel){
        if (uiModel.foodVenues != null && !uiModel.foodVenues.isEmpty()){
            venueListView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            if (venues != null) venues.clear();
            else venues = new ArrayList<>();
            venues.addAll(uiModel.foodVenues);
            venueNames = getListVenueNames(uiModel.foodVenues);
            setUpListView();
        }
    }

    private void updateUIForInProgress(FoodListUIModel uiModel){
        venueListView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void updateUIForError(FoodListUIModel uiModel){

    }

    private void handleError(Throwable error){
        System.out.print(error.getMessage());
    }

    private void handleUiModelObserverOnComplete(){
        uiModelObserverHasOnComplete = true;
        compositeDisposable.remove(uiModelDisposable);
    }

    @Override
    public void openDetailActivity(FoodVenue venue){
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(FoursquareAppConstants.FOOD_VENUE_PARCELABLE, venue);
        startActivity(intent);
    }

    private void setUpListView(){
        // set list adapter to view and update list
        if (adapter == null) { // Not currently allowing user to reload list so adapter always == null
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, venueNames);
            venueListView.setAdapter(adapter);
        } else if (venueListView.getAdapter() == null){
            venueListView.setAdapter(adapter);
            adapter.addAll(venueNames);
        } else adapter.addAll(venueNames);

        // click listener
        venueListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String venueName = (String) parent.getAdapter().getItem(position);
                FoodVenue venue = getFoodVenueFromList(venueName);
                if (venue != null) presenter.uiEvent(UIEvent.openVenueInfo(venue));
            }
        });
    }

    private FoodVenue getFoodVenueFromList(String venueName){
        FoodVenue foodVenue = null;
        if (venueName != null){
            for (FoodVenue venue : venues){
                if (venueName.equals(venue.venueName)) foodVenue = venue;
            }
        }
        return foodVenue;
    }

    private ArrayList<String> getListVenueNames(List<FoodVenue> foodVenues){
        ArrayList<String> venueNamesList = new ArrayList<>();
        for (FoodVenue venue : foodVenues){
            if (venue.venueName != null && !venue.venueName.isEmpty()) venueNamesList.add(venue.venueName);
        }
        return venueNamesList;
    }

}
