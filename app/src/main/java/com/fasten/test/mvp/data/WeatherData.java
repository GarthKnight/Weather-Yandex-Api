package com.fasten.test.mvp.data;

import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class WeatherData {

    Fact fact;
    GeoObject geo_object;
    ArrayList<Forecast> forecasts;

    public Fact getFact() {
        return fact;
    }

    public GeoObject getGeoObject() {
        return geo_object;
    }


    public ArrayList<Forecast> getForecasts() {
        return forecasts;
    }
}
