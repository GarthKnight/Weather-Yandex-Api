package com.fasten.test.dagger;

import android.content.Context;

import com.fasten.test.dagger.module.WeatherModule;


public class AppComponentHelper {
    private static AppComponent appComponent;

    public void build(Context context) {
        appComponent = DaggerAppComponent.builder()
                .weatherModule(new WeatherModule())
                .build();
    }

    public  AppComponent getAppComponent() {
        return appComponent;
    }
}
