package com.pomme.foursquare.injection.subcomponents;

import com.pomme.foursquare.data.DataManager;
import com.pomme.foursquare.ui.ListActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pomme on 2017-10-03.
 *
 * Provides dependencies for
 * @see com.pomme.foursquare.ui.FoodListActivity
 */

@Module
public abstract class ListActivityModule {

    @Provides
    public static ListActivityPresenter provideListActivityPresenter(DataManager manager){
        return new ListActivityPresenter(manager);
    }
}
