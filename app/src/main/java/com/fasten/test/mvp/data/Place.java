package com.fasten.test.mvp.data;

import java.util.ArrayList;

public class Place {
    String name;
    double lat;
    double lon;

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public static String[] getNames(ArrayList<Place> places) {
        String[] names = new String[places.size()];
        for (int i = 0; i < places.size(); i++) {
            names[i] = places.get(i).name;
        }
        return names;
    }
}
