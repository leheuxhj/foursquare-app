package com.pomme.foursquare.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.pomme.foursquare.R;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class FoodListActivity extends AppCompatActivity {

    @Inject ListActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // dependency injection test
        boolean injected = presenter == null ? false : true;
        TextView textView = (TextView) findViewById(R.id.text1);
        textView.setText("Dependency injection worked: " + String.valueOf(injected));

    }
}
