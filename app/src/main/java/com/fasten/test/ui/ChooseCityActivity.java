package com.fasten.test.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ahmadrosid.svgloader.SvgLoader;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.fasten.test.R;
import com.fasten.test.mvp.data.Place;
import com.fasten.test.mvp.data.WUndegraoundPlaceResponse;
import com.fasten.test.mvp.data.WeatherData;
import com.fasten.test.mvp.presenters.ChooseCityPresenter;
import com.fasten.test.mvp.views.ChooseCityView;
import com.fasten.test.ui.adapters.WeatherAdapter;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ChooseCityActivity extends MvpAppCompatActivity implements ChooseCityView {

    private static final String PARCEL_WEATHER = "pr_weather";
    private static final String PARCEL_CHOSEN_PLACE = "pr_chosen_place";

    @BindView(R.id.etCity)
    AutoCompleteTextView etCity;
    @BindView(R.id.rvInfo)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @InjectPresenter
    ChooseCityPresenter presenter;

    private CompositeDisposable compositeDisposable;

    ArrayList<Place> places = new ArrayList<>();
    String chosenPlace = "";
    private WeatherData weatherData;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        ButterKnife.bind(this);
        compositeDisposable = new CompositeDisposable();

        if (savedInstanceState != null) {
            weatherData = Parcels.unwrap(savedInstanceState.getParcelable(PARCEL_WEATHER));
            chosenPlace = savedInstanceState.getString(PARCEL_CHOSEN_PLACE);
        }

        etCity.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, new String[]{}));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (weatherData != null) {
            onGetWeather(weatherData);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initAutoCompleteET();
    }

    private void initAutoCompleteET() {
        compositeDisposable.add(RxTextView
                .textChanges(etCity)
                .debounce(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(charSequence -> {
                    Log.d("API", charSequence.toString());
                    if (charSequence.length() > 0 && !charSequence.toString().equals(chosenPlace)) {
                        enableProgress(true);
                        compositeDisposable.add(presenter.callPlaceAutoComplete(charSequence.toString()));
                    }

                }, Throwable::printStackTrace));
    }

    private void processResponse(WUndegraoundPlaceResponse response) {
        places = response.getPlaces();

        etCity.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, Place.getNames(response.getPlaces())));

        if (!etCity.getText().toString().equals(chosenPlace)) {
            Log.d("AUTOCOMPLETE", "processResponse: ");
            etCity.showDropDown();
        }

        etCity.setOnItemClickListener((adapterView, view, i, l) -> {
            hideKeyboard();
            etCity.clearFocus();
            compositeDisposable.add(presenter.callWeather(places.get(i)));
            chosenPlace = adapterView.getAdapter().getItem(i).toString();

        });
    }

    private void processWeatherData(WeatherData weatherData) {
        WeatherAdapter weatherAdapter = new WeatherAdapter(weatherData) {
            @Override
            public void loadImage(String icon, ImageView ivIcon) {
                SvgLoader.pluck()
                        .with(ChooseCityActivity.this)
                        .load(getString(R.string.icon_url, icon), ivIcon);
            }
        };

        recyclerView.setAdapter(weatherAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        compositeDisposable.dispose();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PARCEL_WEATHER, Parcels.wrap(weatherData));
        outState.putString(PARCEL_CHOSEN_PLACE, chosenPlace);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(etCity.getWindowToken(), 0);
        }
    }

    @Override
    public void onGetPlaces(WUndegraoundPlaceResponse response) {
        processResponse(response);
    }

    @Override
    public void onGetWeather(WeatherData weatherData) {
        this.weatherData = weatherData;
        processWeatherData(weatherData);
    }

    @Override
    public void enableProgress(boolean isEnable) {
        progressBar.setVisibility(isEnable ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onError(Throwable error) {

    }
}
