package com.fasten.test.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.fasten.test.mvp.data.WUndegraoundPlaceResponse;
import com.fasten.test.mvp.data.WeatherData;

public interface ChooseCityView extends BaseView {

    void onGetPlaces(WUndegraoundPlaceResponse wUndegraoundPlaceResponse);

    void onGetWeather(WeatherData weatherData);
}
