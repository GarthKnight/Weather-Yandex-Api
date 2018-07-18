package com.fasten.test.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.fasten.test.mvp.data.WeatherData;

public interface TodayView extends BaseView {
    @StateStrategyType(SingleStateStrategy.class)
    void onForecastLoaded(WeatherData weatherData);
}
