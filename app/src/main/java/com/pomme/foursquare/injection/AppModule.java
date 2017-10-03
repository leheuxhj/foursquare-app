package com.pomme.foursquare.injection;

import android.content.Context;

import com.pomme.foursquare.FoursquareApplication;
import com.pomme.foursquare.data.DataManager;
import com.pomme.foursquare.data.DataManagerImpl;
import com.pomme.foursquare.injection.subcomponents.ListActivitySubComponent;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pomme on 2017-10-03.
 *
 * This module provides application level dependencies for injection.
 * It also references subcomponents.
 */

@Module(subcomponents = ListActivitySubComponent.class)
public class AppModule {

    @Provides
    Context provideContext(FoursquareApplication application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    DataManager provideDataManager(){
        return new DataManagerImpl();
    }

}
