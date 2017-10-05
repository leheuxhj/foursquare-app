package com.pomme.foursquare.ui.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.pomme.foursquare.R;
import com.pomme.foursquare.constants.FoursquareAppConstants;
import com.pomme.foursquare.models.FoodVenue;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * Created by pomme on 2017-10-04.
 */

public class DetailActivity extends AppCompatActivity {

    @Inject
    DetailPresenter presenter;

    private FoodVenue venue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // dependency injection test
        boolean injected = presenter == null ? false : true;
        TextView textView = (TextView) findViewById(R.id.textViewDetail);
        textView.setText("Dependency injection worked: " + String.valueOf(injected));

        //venue info test
        try {
            venue = getIntent().getParcelableExtra(FoursquareAppConstants.FOOD_VENUE_PARCELABLE);
        } catch (ClassCastException e){
            e.getLocalizedMessage();
        }
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        if (venue != null && venue.venueName != null) textView2.setText(venue.venueName);

    }
}
