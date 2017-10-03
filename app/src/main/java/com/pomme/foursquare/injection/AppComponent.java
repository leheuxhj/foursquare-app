package com.pomme.foursquare.injection;

import com.pomme.foursquare.FoursquareApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by pomme on 2017-10-03.
 *
 * AppComponent refers to application level modules.
 * Subcomponents are found in :
 * @see BuildersModule
 */

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, BuildersModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(FoursquareApplication application);
        AppComponent build();
    }
    void inject(FoursquareApplication app);
}