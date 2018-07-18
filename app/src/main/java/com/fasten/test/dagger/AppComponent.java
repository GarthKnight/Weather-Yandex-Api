package com.fasten.test.dagger;


import com.fasten.test.dagger.module.WeatherModule;
import com.fasten.test.mvp.presenters.ChooseCityPresenter;
import com.fasten.test.mvp.presenters.TodayPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {WeatherModule.class})
public interface AppComponent {

     void inject(ChooseCityPresenter presenter);
     void inject(TodayPresenter presenter);

}