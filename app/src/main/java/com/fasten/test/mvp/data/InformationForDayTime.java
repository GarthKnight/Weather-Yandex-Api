package com.fasten.test.mvp.data;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class InformationForDayTime {

    @SerializedName("temp_avg")
    private int temp;
    private String condition;
    private String icon;

    public int getTemp() {
        return temp;
    }

    public String getCondition() {
        return condition;
    }

    public String getIcon() {
        return icon;
    }
}
