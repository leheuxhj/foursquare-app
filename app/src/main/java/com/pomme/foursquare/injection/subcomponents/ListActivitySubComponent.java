package com.pomme.foursquare.injection.subcomponents;


import com.pomme.foursquare.injection.AppComponent;
import com.pomme.foursquare.ui.FoodListActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by pomme on 2017-10-03.
 *
 * Subcomponent of
 * @see AppComponent
 * for
 * @see FoodListActivity
 *
 */

@Subcomponent(modules = ListActivityModule.class)
public interface ListActivitySubComponent extends AndroidInjector<FoodListActivity> {
    @Subcomponent.Builder
    abstract class Builder
            extends AndroidInjector.Builder<FoodListActivity> {}
}
