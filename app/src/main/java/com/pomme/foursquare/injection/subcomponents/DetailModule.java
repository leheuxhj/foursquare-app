package com.pomme.foursquare.injection.subcomponents;

import com.pomme.foursquare.ui.details.DetailPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pomme on 2017-10-04.
 * Provides dependencies for
 * @see com.pomme.foursquare.ui.details.DetailActivity
 */

@Module
public abstract class DetailModule {

    @Provides
    public static DetailPresenter provideDetailPresenter(){
        return new DetailPresenter();
    }
}
