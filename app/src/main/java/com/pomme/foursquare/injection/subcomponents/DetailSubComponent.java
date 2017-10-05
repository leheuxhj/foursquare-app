package com.pomme.foursquare.injection.subcomponents;

import com.pomme.foursquare.ui.details.DetailActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by pomme on 2017-10-04.
 * Subcomponent of
 * @see com.pomme.foursquare.injection.AppComponent
 * for
 * @see DetailActivity
 *
 */

@Subcomponent(modules = DetailModule.class)
public interface DetailSubComponent extends AndroidInjector<DetailActivity> {
    @Subcomponent.Builder
    abstract class Builder
            extends AndroidInjector.Builder<DetailActivity> {}
}