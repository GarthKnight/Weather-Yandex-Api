package com.fasten.test.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.fasten.test.api.TestAppApi;
import com.fasten.test.dagger.ComponentManager;
import com.fasten.test.mvp.views.TodayView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class TodayPresenter  extends MvpPresenter<TodayView>{

    private static final int WEEK_LENGTH = 7;

    @Inject
    TestAppApi api;


    public TodayPresenter() {
        ComponentManager.get().getAppComponent().inject(this);
    }

    public Disposable getForecastDisposable(double lat, double lng){
        return new TestAppApi().getService()
                .getForecastByLatLng(lat, lng, WEEK_LENGTH,true)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnTerminate(() -> getViewState().enableProgress(false))
                .subscribe(weatherData -> {
                    getViewState().onForecastLoaded(weatherData);
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().onError(throwable);
                });

    }
}
