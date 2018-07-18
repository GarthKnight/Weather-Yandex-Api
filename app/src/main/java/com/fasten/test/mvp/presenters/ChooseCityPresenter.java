package com.fasten.test.mvp.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.fasten.test.api.TestAppApi;
import com.fasten.test.dagger.ComponentManager;
import com.fasten.test.mvp.data.Place;
import com.fasten.test.mvp.data.WUndegraoundPlaceResponse;
import com.fasten.test.mvp.views.ChooseCityView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ChooseCityPresenter extends MvpPresenter<ChooseCityView> {

    private static final int WEEK_LENGTH = 7;

    @Inject
    TestAppApi api;



    public ChooseCityPresenter() {
        ComponentManager.get().getAppComponent().inject(this);
    }

    public Disposable callPlaceAutoComplete(String s) {
        Log.d("API", " getPlaces: " + s);
        return api.getService()
                .getPlaces(s)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnTerminate(() -> getViewState().enableProgress(false))
                .subscribe(new Consumer<WUndegraoundPlaceResponse>() {
                               @Override
                               public void accept(WUndegraoundPlaceResponse wUndegraoundPlaceResponse) throws Exception {
                                   Log.d("API", "onGetPlaces done! Try to change view");
                                   ChooseCityPresenter.this.getViewState().onGetPlaces(wUndegraoundPlaceResponse);
                               }
                           },
                        Throwable::printStackTrace);
    }

    public Disposable callWeather(Place place) {
        return api.getService()
                .getForecastByLatLng(place.getLat(), place.getLon(), WEEK_LENGTH, true)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnTerminate(() -> getViewState().enableProgress(false))
                .subscribe(weatherData -> getViewState().onGetWeather(weatherData),
                        Throwable::printStackTrace);
    }


}
