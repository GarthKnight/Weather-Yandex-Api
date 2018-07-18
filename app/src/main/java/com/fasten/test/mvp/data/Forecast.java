package com.fasten.test.mvp.data;

import org.parceler.Parcel;

@Parcel
public class Forecast {

    String date;
    ForecastsForDayTime parts;

    public String getDate() {
        return date;
    }

    public ForecastsForDayTime getParts() {
        return parts;
    }
}
