package com.pomme.foursquare;

import android.app.Activity;
import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.pomme.foursquare.injection.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.fabric.sdk.android.Fabric;

/**
 * Created by pomme on 2017-10-03.
 */

public class FoursquareApplication extends Application
        implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Crash and error reporting
        Fabric.with(this, new Crashlytics());

        // Dependency injection
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);
    }
}
