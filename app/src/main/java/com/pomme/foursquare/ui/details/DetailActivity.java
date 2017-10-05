package com.pomme.foursquare.ui.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.pomme.foursquare.R;
import com.pomme.foursquare.constants.FoursquareAppConstants;
import com.pomme.foursquare.models.FoodVenue;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

/**
 * Created by pomme on 2017-10-04.
 */

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    @Inject
    DetailPresenter presenter;

    @BindView(R.id.detailVenueName)
    TextView venueNameTextView;
    @BindView(R.id.detailVenueAddress)
    TextView venueAddressTextView;
    @BindView(R.id.detailVenueCategory)
    TextView venueCategoryTextView;
    @BindView(R.id.detailErrorMessage)
    TextView errorMessageTextView;

    @BindViews({R.id.detailVenueName, R.id.detailVenueAddress, R.id.detailVenueCategory})
    List<View> detailInfoViews;

    static final ButterKnife.Setter<View, Integer> VISIBLE = new ButterKnife.Setter<View, Integer>() {
        @Override
        public void set(View view, Integer value, int index) {
            view.setVisibility(value);
        }
    };

    private FoodVenue venue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        //get venue info from parcelable
        try {
            venue = getIntent().getParcelableExtra(FoursquareAppConstants.FOOD_VENUE_PARCELABLE);
        } catch (ClassCastException e){
            e.getLocalizedMessage();
        }

        // display venue or error
        if (venue != null) bindVenueDetailsToView(venue);
        else presenter.onError(venue);
    }

    @Override
    public void displayErrorMessage() {
        ButterKnife.apply(detailInfoViews, VISIBLE, View.GONE);
        errorMessageTextView.setVisibility(View.VISIBLE);
    }

    private void bindVenueDetailsToView(FoodVenue venue){
        ButterKnife.apply(detailInfoViews, VISIBLE, View.VISIBLE);
        errorMessageTextView.setVisibility(View.GONE);
        if (venue.venueName != null) venueNameTextView.setText(venue.venueName);
        if (venue.address != null) venueAddressTextView.setText(venue.address);
        if (venue.category != null) venueCategoryTextView.setText(venue.category);
    }

}
