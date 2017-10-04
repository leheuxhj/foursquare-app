package com.pomme.foursquare.injection.subcomponents;

import com.pomme.foursquare.data.DataManager;
import com.pomme.foursquare.ui.foodlist.FoodListPresenter;
import com.pomme.foursquare.ui.foodlist.FoodListActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pomme on 2017-10-03.
 *
 * Provides dependencies for
 * @see FoodListActivity
 */

@Module
public abstract class ListActivityModule {

    @Provides
    public static FoodListPresenter provideListActivityPresenter(DataManager manager){
        return new FoodListPresenter(manager);
    }
}
