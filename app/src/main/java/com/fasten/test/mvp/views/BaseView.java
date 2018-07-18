package com.fasten.test.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface BaseView extends MvpView {
    @StateStrategyType(SingleStateStrategy.class)
    void enableProgress(boolean isEnable);

    @StateStrategyType(SingleStateStrategy.class)
    void onError(Throwable error);
}