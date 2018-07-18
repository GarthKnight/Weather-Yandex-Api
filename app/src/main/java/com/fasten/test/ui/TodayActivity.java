package com.fasten.test.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ahmadrosid.svgloader.SvgLoader;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.fasten.test.R;
import com.fasten.test.mvp.data.WeatherData;
import com.fasten.test.mvp.presenters.TodayPresenter;
import com.fasten.test.mvp.views.TodayView;
import com.fasten.test.ui.adapters.WeatherAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class TodayActivity extends MvpAppCompatActivity implements TodayView {
    public static String EXTRAS_LAT = "lat";
    public static String EXTRAS_LNG = "lng";

    @BindView(R.id.rvWeather)
    RecyclerView rvWeather;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @InjectPresenter
    TodayPresenter presenter;
    double lat;
    double lng;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_city);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            //default krasnodar coords
            lng = getIntent().getDoubleExtra(EXTRAS_LNG, 38.9760300);
            lat = getIntent().getDoubleExtra(EXTRAS_LAT, 45.0448400);
        } else {
            lng = savedInstanceState.getDouble(EXTRAS_LNG, 38.9760300);
            lat = savedInstanceState.getDouble(EXTRAS_LAT, 45.0448400);
        }


        //load from backend
        enableProgress(true);
        Disposable disposable = presenter.getForecastDisposable(lat, lng);
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.dispose();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(EXTRAS_LAT, lat);
        outState.putDouble(EXTRAS_LNG, lng);
    }

    @Override
    public void onForecastLoaded(WeatherData weatherData) {
        WeatherAdapter weatherAdapter = new WeatherAdapter(weatherData){
            @Override
            public void loadImage(String icon, ImageView ivIcon) {
                SvgLoader.pluck()
                        .with(TodayActivity.this)
                        .load(getString(R.string.icon_url, icon), ivIcon);
            }
        };
        rvWeather.setLayoutManager(new LinearLayoutManager(this));
        rvWeather.setAdapter(weatherAdapter);
    }


    @Override
    public void enableProgress(boolean isEnable) {
        progressBar.setVisibility(isEnable ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onError(Throwable error) {

    }
}
