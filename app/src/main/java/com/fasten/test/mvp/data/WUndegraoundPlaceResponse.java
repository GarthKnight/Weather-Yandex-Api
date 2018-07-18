package com.fasten.test.mvp.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WUndegraoundPlaceResponse {
    @SerializedName("RESULTS")
    ArrayList<Place> places;

    public ArrayList<Place> getPlaces() {
        return places;
    }
}
