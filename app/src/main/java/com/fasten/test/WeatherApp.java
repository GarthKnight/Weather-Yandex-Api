package com.fasten.test;

import android.app.Application;

import com.fasten.test.dagger.ComponentManager;

public class WeatherApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ComponentManager.init(this);
    }
}
