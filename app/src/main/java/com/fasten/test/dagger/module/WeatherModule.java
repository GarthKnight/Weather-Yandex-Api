package com.fasten.test.dagger.module;

import com.fasten.test.api.TestAppApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class WeatherModule {

    public WeatherModule() {

    }


    @Provides
    @Singleton
    TestAppApi provideApi() {
        return new TestAppApi();
    }


}