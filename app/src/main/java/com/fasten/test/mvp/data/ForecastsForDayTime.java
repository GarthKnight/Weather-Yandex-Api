package com.fasten.test.mvp.data;

import org.parceler.Parcel;

@Parcel
public class ForecastsForDayTime {

    private InformationForDayTime day;
    private InformationForDayTime night;

    public InformationForDayTime getDay() {
        return day;
    }

    public InformationForDayTime getNight() {
        return night;
    }
}
