package com.pomme.foursquare.ui.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.pomme.foursquare.R;
import com.pomme.foursquare.constants.FoursquareAppConstants;
import com.pomme.foursquare.models.FoodVenue;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

/**
 * Created by pomme on 2017-10-04.
 */

public class DetailActivity extends AppCompatActivity {

    @Inject
    DetailPresenter presenter;

    @BindView(R.id.detailVenueName)
    TextView venueNameTextView;
    @BindView(R.id.detailVenueAddress)
    TextView venueAddressTextView;
    @BindView(R.id.detailVenueCategory)
    TextView venueCategoryTextView;

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

        if (venue != null) bindVenueDetailsToView(venue);
        else displayErrorMessage();
    }

    private void displayErrorMessage(){

    }

    private void bindVenueDetailsToView(FoodVenue venue){
        if (venue.venueName != null) venueNameTextView.setText(venue.venueName);
        if (venue.address != null) venueAddressTextView.setText(venue.address);
        if (venue.category != null) venueCategoryTextView.setText(venue.category);
    }

}
