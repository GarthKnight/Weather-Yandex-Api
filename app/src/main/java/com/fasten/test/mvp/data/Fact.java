package com.fasten.test.mvp.data;

import org.parceler.Parcel;

@Parcel
public class Fact {


    int temp;
    String condition;
    double wind_speed;
    int pressure_mm;
    int humidity;
    int prec_type;
    String icon;

    public int getTemp() {
        return temp;
    }

    public String getCondition() {
        return condition;
    }

    public String getWindSpeed() {
        return String.valueOf(wind_speed) + "mps";
    }

    public String getPressureMm() {
        return String.valueOf(pressure_mm) + "mm Hg";
    }

    public String getHumidity() {
        return String.valueOf(humidity) + "%";
    }

    public int getPrecType() {
        return prec_type;
    }

    public String getIcon() {
        return icon;
    }

    public String getPrecDesc() {

        switch (prec_type) {
            case 0:
                return "No rain";
            case 1:
                return "Rain";
            case 2:
                return "Rain with snow";
            case 3:
                return "Snow";
        }
        return "Forbidden";
    }
}
