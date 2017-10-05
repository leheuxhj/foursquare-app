package com.pomme.foursquare.injection;

import android.app.Activity;

import com.pomme.foursquare.injection.subcomponents.DetailSubComponent;
import com.pomme.foursquare.injection.subcomponents.ListActivitySubComponent;
import com.pomme.foursquare.ui.details.DetailActivity;
import com.pomme.foursquare.ui.foodlist.FoodListActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by pomme on 2017-10-03.
 *
 * Provides bindings for sub components to the component that injects FoursquareApplication:
 * @see AppComponent
 *
 * Refer https://google.github.io/dagger/android.html
 */

@Module
public abstract class BuildersModule {
    @Binds
    @IntoMap
    @ActivityKey(FoodListActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
    bindListActivityInjectorFactory(ListActivitySubComponent.Builder builder);

    @Binds
    @IntoMap
    @ActivityKey(DetailActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
    bindDetailActivityInjectorFactory(DetailSubComponent.Builder builder);
}
