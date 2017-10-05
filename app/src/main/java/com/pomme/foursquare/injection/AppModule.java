package com.pomme.foursquare.injection;

import android.content.Context;

import com.pomme.foursquare.BuildConfig;
import com.pomme.foursquare.FoursquareApplication;
import com.pomme.foursquare.data.DataManager;
import com.pomme.foursquare.data.DataManagerImpl;
import com.pomme.foursquare.data.FoursquareEndPoint;
import com.pomme.foursquare.injection.subcomponents.DetailSubComponent;
import com.pomme.foursquare.injection.subcomponents.ListActivitySubComponent;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pomme on 2017-10-03.
 *
 * This module provides application level dependencies for injection.
 * It also references subcomponents.
 */

@Module(subcomponents = {ListActivitySubComponent.class, DetailSubComponent.class})
public class AppModule {

    private static final String FOURSQUARE_URL = "https://api.foursquare.com";

    @Provides
    Context provideContext(FoursquareApplication application) {
        return application.getApplicationContext();
    }

    @Provides
    public OkHttpClient.Builder provideOkHttpLogger() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        return httpClient;
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(OkHttpClient.Builder okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(FOURSQUARE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient.build())
                .build();
    }

    @Provides
    public FoursquareEndPoint provideFoursquareEndPoint(Retrofit retrofit) {
        return retrofit.create(FoursquareEndPoint.class);
    }

    @Provides
    @Singleton
    DataManager provideDataManager(Context context, FoursquareEndPoint foursquareEndPoint){
        return new DataManagerImpl(context, foursquareEndPoint);
    }

}
