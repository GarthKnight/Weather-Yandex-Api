package com.fasten.test.api;


import com.fasten.test.mvp.data.WUndegraoundPlaceResponse;
import com.fasten.test.mvp.data.WeatherData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface API {

    @Headers("X-Yandex-API-Key: 80dfa71a-41e5-44ca-89b3-676f4aba1784")
    @GET("https://api.weather.yandex.ru/v1/forecast")
    Observable<WeatherData> getForecastByLatLng(@Query("lat") double lat,
                                                           @Query("lon") double lng,
                                                           @Query("limit") int limit,
                                                           @Query("extra") boolean extra);


    @GET("https://autocomplete.wunderground.com/aq")
    Observable<WUndegraoundPlaceResponse> getPlaces(@Query("query") String query);
}
