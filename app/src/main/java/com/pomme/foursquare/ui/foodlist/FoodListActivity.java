package com.pomme.foursquare.ui.foodlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.pomme.foursquare.R;
import com.pomme.foursquare.constants.FoursquareAppConstants;
import com.pomme.foursquare.models.FoodVenue;
import com.pomme.foursquare.ui.UIEvent;
import com.pomme.foursquare.ui.details.DetailActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FoodListActivity extends AppCompatActivity implements FoodListContract.View {

    @Inject
    FoodListPresenter presenter;

    private CompositeDisposable compositeDisposable;
    private Disposable uiModelDisposable;
    private boolean uiModelObserverHasOnComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        presenter.setView(this);
        setContentView(R.layout.activity_list);

        // dependency injection test
        boolean injected = presenter == null ? false : true;
        TextView textView = (TextView) findViewById(R.id.text1);
        textView.setText("Dependency injection worked: " + String.valueOf(injected));

        // setup test buttons
        View button = findViewById(R.id.button);
        button.setOnClickListener((View v) -> presenter.uiEvent(UIEvent.newListRequest()));
        View button2 = findViewById(R.id.button2);
        button2.setOnClickListener((View v) -> presenter.uiEvent(UIEvent.openVenueInfo("Pavlova cafe")));
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        presenter.uiEvent(UIEvent.newListRequest());
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
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
        System.out.print(uiModel);
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

}
